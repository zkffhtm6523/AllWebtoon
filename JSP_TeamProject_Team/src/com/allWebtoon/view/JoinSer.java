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
import com.allWebtoon.util.SecurityUtils;
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
		String nm = request.getParameter("name");
		String email = request.getParameter("email");
		String birth = request.getParameter("birth");
		String gender = request.getParameter("gender");
		String u_profile = request.getParameter("u_profile");
		String chkProfile = request.getParameter("chkProfile");
		int u_joinPath = Integer.parseInt(request.getParameter("u_joinPath"));
		
		UserVO param = new UserVO();
		param.setU_id(u_id);
		param.setU_password(u_pw);
		param.setU_name(nm);
		param.setU_email(email);
		param.setU_birth(birth);
		param.setGender_name(gender);
		param.setU_profile(u_profile);
		param.setChkProfile(chkProfile);
		param.setU_joinPath(u_joinPath);
		
		int result= UserDAO.insUser(param);
	
		if(result != 1) {
			//'에러가 발생하였습니다. 관리자에게 문의 ㄱ'
			request.setAttribute("msg", "에러가 발생했습니다. 관리자에게 문의 ㄱ");
			request.setAttribute("data", param);
			//ViewResolver.forward("user/join", request, response);
			doGet(request, response);
			return;
		}
		
		UserDAO.selUser(param);
		System.out.println(param.getR_dt());
		System.out.println(param.getM_dt());
		HttpSession hs = request.getSession();
		hs.setAttribute(Const.LOGIN_USER,param);
		
		response.sendRedirect("/webtoon/cmt");
		
	}
}
