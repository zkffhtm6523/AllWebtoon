package com.allWebtoon.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.allWebtoon.dao.UserDAO;
import com.allWebtoon.util.Const;
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
		String u_id = request.getParameter("u_id");
		String u_pw = request.getParameter("u_pw");
		String encrypt_pw = MyUtils.encryptString(u_pw);			//비밀번호 암호화
		String nm = request.getParameter("name");
		String email = request.getParameter("email");
		String birth = request.getParameter("birth");
		String gender = request.getParameter("gender");
		
		UserVO param = new UserVO();
		param.setU_id(u_id);
		param.setU_password(encrypt_pw);
		param.setU_name(nm);
		param.setU_email(email);
		param.setU_birth(birth);
		param.setGender_name(gender);
		
		int result= UserDAO.insUser(param);
	
		if(result != 1) {
			//'에러가 발생하였습니다. 관리자에게 문의 ㄱ'
			request.setAttribute("msg", "에러가 발생했습니다. 관리자에게 문의 ㄱ");
			request.setAttribute("data", param);
			//ViewResolver.forward("user/join", request, response);
			doGet(request, response);
			return;
		}
		
		UserDAO.login(param);
		
		

		HttpSession hs = request.getSession();
		hs.setAttribute(Const.LOGIN_USER,param);
		
		UserVO vo = (UserVO) hs.getAttribute(Const.LOGIN_USER);
		
	//	System.out.println(vo.getU_no());
		response.sendRedirect("/webtoon/cmt");
		
		//response.sendRedirect("/");
	}
}
