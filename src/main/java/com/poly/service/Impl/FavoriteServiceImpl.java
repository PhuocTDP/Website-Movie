package com.poly.service.Impl;

import java.sql.Timestamp;
import java.util.List;

import com.poly.dao.FavoriteDao;
import com.poly.dao.UserDao;
import com.poly.dao.impl.FavoriteDaoImpl;
import com.poly.dao.impl.UserDaoImpl;
import com.poly.entity.Favorite;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.FavoriteService;
import com.poly.service.UserService;
import com.poly.service.VideoService;

public class FavoriteServiceImpl implements FavoriteService {

	private FavoriteDao dao;
	private VideoService videoService = new VideoServiceImpl();

	public FavoriteServiceImpl() {
		dao = new FavoriteDaoImpl();
	}

	@Override
	public List<Favorite> findByUser(String username) {
		return dao.findByUser(username);
	}

	@Override
	public List<Favorite> findByUserAndIsLiked(String username) {
		return dao.findByUserAndIsLiked(username);
	}

	@Override
	public Favorite findByUserIdAndVideoId(Integer userId, Integer videoId) {
		return dao.findByUserIdAndVideoId(userId, videoId);
	}

	@Override
	public Favorite create(User user, Video video) {
		Favorite existFavorite = findByUserIdAndVideoId(user.getId(), video.getId());
		if (existFavorite == null) {
			existFavorite = new Favorite();
			existFavorite.setUser(user);
			existFavorite.setVideo(video);
			existFavorite.setViewDate(new Timestamp(System.currentTimeMillis()));
			existFavorite.setIsLiked(Boolean.FALSE);
			return dao.create(existFavorite);
		}
		return existFavorite;
	}

	@Override
	public boolean updateLikeOrUnlike(User user, String videoHref) {
		Video video = videoService.findByHref(videoHref);
		Favorite existFavorite = findByUserIdAndVideoId(user.getId(), video.getId());
		if (existFavorite.getIsLiked() == Boolean.FALSE) {
			existFavorite.setIsLiked(Boolean.TRUE);
			existFavorite.setLikeDate(new Timestamp(System.currentTimeMillis()));
		} else {
			existFavorite.setIsLiked(Boolean.FALSE);
			existFavorite.setLikeDate(null);
		}
		Favorite updatedFavorite = dao.update(existFavorite);
		return updatedFavorite != null ? true : false;
	}

}
