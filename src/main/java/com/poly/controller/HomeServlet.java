package com.poly.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import com.poly.constant.SessionAtt;
import com.poly.entity.Favorite;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.FavoriteService;
import com.poly.service.VideoService;
import com.poly.service.Impl.FavoriteServiceImpl;
import com.poly.service.Impl.VideoServiceImpl;

@WebServlet(urlPatterns = { "/index", "/favorites", "/history" })
public class HomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7533448599497693108L;
	private static final int VIDEO_MAX_PAGE_SIZE = 2;
	private VideoService videoService = new VideoServiceImpl();
	private FavoriteService favoriteService = new FavoriteServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String path = req.getServletPath();
		switch (path) {
		case "/index":
			doGetIndex(req, resp);
			break;
		case "/favorites":
			doGetFavotites(session,req, resp);
			break;
		case "/history":
			doGetHistory(session, req, resp);
			break;
		}

	}

	private void doGetHistory(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user =(User) session.getAttribute(SessionAtt.CURRENT_USER);
		List<Favorite> favorites = favoriteService.findByUser(user.getUsername());
		List<Video> videos = new ArrayList<>();
		favorites.forEach(item -> videos.add(item.getVideo()));
		req.setAttribute("videos", videos);
		RequestDispatcher reqd = req.getRequestDispatcher("/view/user/history.jsp");
		reqd.forward(req, resp);

		
	}

	private void doGetFavotites(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user =(User) session.getAttribute(SessionAtt.CURRENT_USER);
		List<Favorite> favorites = favoriteService.findByUserAndIsLiked(user.getUsername());
		List<Video> videos = new ArrayList<>();
		favorites.forEach(item -> videos.add(item.getVideo()));
		req.setAttribute("videos", videos);
		RequestDispatcher reqd = req.getRequestDispatcher("/view/user/favorites.jsp");
		reqd.forward(req, resp);

		}

	private void doGetIndex(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Video> countVideo = videoService.findAll();
		int maxPage = (int) Math.ceil(countVideo.size() / (double) VIDEO_MAX_PAGE_SIZE);
		req.setAttribute("maxPage", maxPage);
		List<Video> videos;
		String pageNumber = req.getParameter("page");
		if(pageNumber == null || Integer.valueOf(pageNumber) > maxPage) {
			videos = videoService.findAll(1, VIDEO_MAX_PAGE_SIZE);
			req.setAttribute("currentPage", 1);
		}else {
			videos = videoService.findAll(Integer.valueOf(pageNumber), VIDEO_MAX_PAGE_SIZE);
			req.setAttribute("currentPage", Integer.valueOf(pageNumber));
		}
		

		req.setAttribute("videos", videos);
		RequestDispatcher reqd = req.getRequestDispatcher("/view/user/index.jsp");
		reqd.forward(req, resp);

	}
}
