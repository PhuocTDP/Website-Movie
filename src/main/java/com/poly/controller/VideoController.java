package com.poly.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poly.constant.SessionAtt;
import com.poly.entity.Favorite;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.FavoriteService;
import com.poly.service.VideoService;
import com.poly.service.Impl.FavoriteServiceImpl;
import com.poly.service.Impl.VideoServiceImpl;

@WebServlet(urlPatterns = "/video")
public class VideoController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7388741981193937759L;

	private VideoService videoService = new VideoServiceImpl();

	private FavoriteService favoriteService = new FavoriteServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String actionParam = req.getParameter("action");
		String href = req.getParameter("id");
		HttpSession session = req.getSession();

		switch (actionParam) {
		case "watch":
			doGetWatch(session, href, req, resp);
			break;
		case "like":
			doGetLike(session, href, req, resp);
			break;
		}
	}

	private void doGetLike(HttpSession session, String href, HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/json");
		User currentUser = (User) session.getAttribute(SessionAtt.CURRENT_USER);
		Boolean rerult = favoriteService.updateLikeOrUnlike(currentUser, href);
		if (rerult == true) {
			resp.setStatus(204);
		} else {
			resp.setStatus(400);
		}
	}

	private void doGetWatch(HttpSession session, String href, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Video video = videoService.findByHref(href);
		req.setAttribute("video", video);
		User currentUser = (User) session.getAttribute(SessionAtt.CURRENT_USER);
		if (currentUser != null) {
			Favorite favorite = favoriteService.create(currentUser, video);
			req.setAttribute("flagLikedBtn", favorite.getIsLiked());
		}
		RequestDispatcher reqd = req.getRequestDispatcher("/view/user/video-detail.jsp");
		reqd.forward(req, resp);
	}

}
