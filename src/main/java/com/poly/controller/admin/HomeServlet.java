package com.poly.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.constant.SessionAtt;
import com.poly.dto.UserDto;
import com.poly.dto.VideoLikedInfo;
import com.poly.entity.User;
import com.poly.service.StaffService;
import com.poly.service.UserService;
import com.poly.service.Impl.StaffServiceImpl;
import com.poly.service.Impl.UserServiceImpl;

@WebServlet(urlPatterns = {"/admin", "/admin/favorites"}, name = "HomeControllerOfAdmin")
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 7533448599497693108L;
	private StaffService staffService = new StaffServiceImpl();
	private UserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute(SessionAtt.CURRENT_USER);

		if (currentUser != null && currentUser.getIsAdmin() == Boolean.TRUE) {
			String path = req.getServletPath();
			switch (path) {
			case "/admin":
				doGetHome(req, resp);
				break;
			case "/admin/favorites":
				doGetFavotites(req, resp);
				break;
			}
		
		}else {
			resp.sendRedirect("index");
		}

	}

	private void doGetFavotites(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		String videoHref = req.getParameter("href");
		List<UserDto> users = userService.findUsersLikedVideoByVideoHref(videoHref);
		if(users.isEmpty()) {
			resp.setStatus(400);
		}else {
			ObjectMapper mapper = new ObjectMapper();
			String dataResp = mapper.writeValueAsString(users);
			resp.setStatus(200);
			out.print(dataResp);
			out.flush();
		}

	}

	private void doGetHome(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		List<VideoLikedInfo> videos = staffService.findVideoLikedInfo();
		req.setAttribute("videos", videos);
		RequestDispatcher reqd = req.getRequestDispatcher("/view/admin/home.jsp");
		reqd.forward(req, resp);
	}

}
