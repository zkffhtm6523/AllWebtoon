package com.allWebtoon.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allWebtoon.dao.UserDAO;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;

@WebServlet("/choGenre")
public class ChoGenreSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String u_no = request.getParameter("u_no");
		request.setAttribute("u_no", u_no);
		ViewResolver.accessForward("choGenre", request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] genres = request.getParameterValues("genre_arr");
		String u_id = request.getParameter("u_id");
		UserVO param = new UserVO();
		param.setU_genre(genres);
		param.setU_id(u_id);
		
		for(String str : param.getU_genre()) {
			UserDAO.insU_genre(param, str);
		}
		
		response.sendRedirect("/");	
	}

}
