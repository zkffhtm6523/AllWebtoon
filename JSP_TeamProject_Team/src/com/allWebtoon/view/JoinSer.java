package com.allWebtoon.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allWebtoon.dao.UserDAO;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;

@WebServlet("/join")
public class JoinSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ViewResolver.accessForward("join", request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_id = request.getParameter("id");
		String user_pw = request.getParameter("pw");
		String encrypt_pw = MyUtils.encryptString(user_pw);			//비밀번호 암호화
		String nm = request.getParameter("name");
		String email = request.getParameter("email");
		String birth = request.getParameter("birth");
		String gender = request.getParameter("gender");
		
		UserVO param = new UserVO();
		param.setUser_id(user_id);
		param.setUser_password(encrypt_pw);
		param.setName(nm);
		param.setEmail(email);
		param.setBirth(birth);
		param.setGender(gender);
		
		int result= UserDAO.insUser(param);
		
		
		if(result != 1) {
			//'에러가 발생하였습니다. 관리자에게 문의 ㄱ'
			request.setAttribute("msg", "에러가 발생했습니다. 관리자에게 문의 ㄱ");
			request.setAttribute("data", param);
			//ViewResolver.forward("user/join", request, response);
			doGet(request, response);
			return;
		}
		
		response.sendRedirect("/choGenre?user_id="+ param.getUser_id());
		
	}
}
