package com.poly.dao.impl;

import java.util.List;

import com.poly.dao.AbstractDao;
import com.poly.dao.FavoriteDao;

import com.poly.entity.Favorite;


public class FavoriteDaoImpl  extends AbstractDao<Favorite> implements FavoriteDao{

	@Override
	public List<Favorite> findByUser(String username) {
		String sql = "select o from Favorite o where o.user.username = ?0 and o.video.isActive = 1"
				+ " order by o.viewDate desc";
		return super.findMany(Favorite.class, sql, username);
	}

	@Override
	public List<Favorite> findByUserAndIsLiked(String username) {
		String sql = "select o from Favorite o where o.user.username = ?0 and o.isLiked =1"
				+ " and o.video.isActive = 1"
				+ " order by o.viewDate desc";
		return super.findMany(Favorite.class, sql, username);
	}

	@Override
	public Favorite findByUserIdAndVideoId(Integer userId, Integer videoId) {
		String sql = "select o from Favorite o where o.user.id = ?0 and o.video.id = ?1"
				+ " and o.video.isActive = 1";
		return super.findOne(Favorite.class, sql, userId, videoId);
	}

}
