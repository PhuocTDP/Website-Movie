package com.poly.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.poly.dao.AbstractDao;
import com.poly.dao.StaffDao;
import com.poly.dto.VideoLikedInfo;
import com.poly.entity.User;

public class StaffDaoImpl extends AbstractDao<Object[]> implements StaffDao{

	@Override
	public List<VideoLikedInfo> findVideoLikedInfo() {
		String sql = "select v.id, v.tilte, v.href, cast(sum(f.isLiked) as float) as totalLike"
				+ " from video v left join favorite f on v.id = f.videoId"
				+ " where v.isActive = 1"
				+ " group by v.id, v.tilte, v.href"
				+ " order by cast(sum(f.isLiked) as float) desc";
		List<Object[]> objects = super.findManyByNativeQuery(sql);
		List<VideoLikedInfo> result = new ArrayList<>();
		objects.forEach(object ->{
			VideoLikedInfo likedInfo = setDataVideoLikedInfo(object);
			result.add(likedInfo);
		});
		return result;
	}
	private VideoLikedInfo setDataVideoLikedInfo(Object[] object) {
		VideoLikedInfo likedInfo = new VideoLikedInfo();
		likedInfo.setVideoId((Integer)object[0]);
		likedInfo.setTilte((String) object[1]);
		likedInfo.setHref((String) object[2]);
		likedInfo.setTotalLike(object[3] == null ? 0: (Float) object[3]);
		return likedInfo;
	}

	

	
}
