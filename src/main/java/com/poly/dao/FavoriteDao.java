package com.poly.dao;

import java.util.List;

import com.poly.entity.Favorite;

public interface FavoriteDao {

	List<Favorite> findByUser(String username);
	List<Favorite> findByUserAndIsLiked(String username);
	Favorite findByUserIdAndVideoId(Integer userId, Integer videoId);
	Favorite create (Favorite entity);
	Favorite update (Favorite entity);

}
