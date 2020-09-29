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
		
		//String u_salt = SecurityUtils.generateSalt();
		//String encrypt = SecurityUtils.getEncrypt(u_pw, u_salt);
		
		//param.setU_salt(u_salt);
		//param.setU_password(encrypt);
		
		System.out.println(param.getU_id());
		System.out.println(param.getU_password());
		System.out.println(param.getU_name());
		System.out.println(param.getU_email());
		System.out.println(param.getU_birth());
		System.out.println(param.getGender_name());
		
		int result= UserDAO.insUser(param);
	
		System.out.println("result : "  + result);
		if(result != 1) {
			//'에러가 발생하였습니다. 관리자에게 문의 ㄱ'
			request.setAttribute("msg", "에러가 발생했습니다. 관리자에게 문의 ㄱ");
			request.setAttribute("data", param);
			//ViewResolver.forward("user/join", request, response);
			doGet(request, response);
			return;
		}
		
		param.setU_birth(null);
		
		int result2 = UserDAO.selUser(param);
		System.out.println("result2 : "  + result2);
		System.out.println(param.getR_dt());
		System.out.println(param.getM_dt());
		
		System.out.println("가입 후 u_no : "  + param.getU_no());
		HttpSession hs = request.getSession();
		hs.setAttribute(Const.LOGIN_USER,param);
		
		response.sendRedirect("/webtoon/cmt");
		
	}
}
