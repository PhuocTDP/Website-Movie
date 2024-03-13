package com.poly.service;

import java.util.List;

import com.poly.entity.Favorite;
import com.poly.entity.User;
import com.poly.entity.Video;

public interface FavoriteService {

	List<Favorite> findByUser(String username);
	List<Favorite> findByUserAndIsLiked(String username);
	Favorite findByUserIdAndVideoId(Integer userId, Integer videoId);
	Favorite create (User user, Video video);
	boolean updateLikeOrUnlike (User user, String videoHref);

}
