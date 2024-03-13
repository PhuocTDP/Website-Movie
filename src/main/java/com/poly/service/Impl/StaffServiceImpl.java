package com.poly.service.Impl;

import java.util.List;

import javax.servlet.ServletContext;

import com.poly.dao.StaffDao;
import com.poly.dao.impl.StaffDaoImpl;
import com.poly.dto.VideoLikedInfo;
import com.poly.service.StaffService;



public class StaffServiceImpl implements StaffService {

	private StaffDao staffDao;
	
	public StaffServiceImpl() {
		staffDao = new StaffDaoImpl();
	}
	
	@Override
	public List<VideoLikedInfo> findVideoLikedInfo() {
		return staffDao.findVideoLikedInfo();
	}

}
