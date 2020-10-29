package com.allWebtoon.access;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import com.allWebtoon.dao.UserDAO;
import com.allWebtoon.util.Const;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.SecurityUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/join")
public class JoinSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ViewResolver.accessForward("join", request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserVO param = new UserVO();
		String u_id = request.getParameter("u_id");
		String u_pw = request.getParameter("u_pw");
		String nm = request.getParameter("name");
		String email = request.getParameter("email");
		String birth = request.getParameter("birth");
		String gender = request.getParameter("gender");
		String u_profile = request.getParameter("u_profile");
		String chkProfile = request.getParameter("chkProfile");
		int u_joinPath = Integer.parseInt(request.getParameter("u_joinPath"));
		
		
		param.setU_id(u_id);
		param.setU_password(u_pw);
		
		//아이디, 패스워드만 넣고 아이디 중복 확인 
		int sel_user = UserDAO.selUser(param);
		
		
		//중복 확인 후 나머지 정보 넣고   
		param.setU_name(nm);
		param.setU_email(email);
		param.setU_birth(birth);
		param.setGender_name(gender);
		param.setU_profile(u_profile);
		param.setChkProfile(chkProfile);
		param.setU_joinPath(u_joinPath);
		
		
		
		//아이디가 있으면  
		if(sel_user == 1 || sel_user == 2) {
			request.setAttribute("msg", "이미 존재하는 아이디 입니다.");
			request.setAttribute("data", param);
			//ViewResolver.forward("user/join", request, response);
			doGet(request, response);
			return;
		}
		
		
		int result= UserDAO.insUser(param);
		
		if(result != 1) {
			//'에러가 발생하였습니다. 관리자에게 문의 ㄱ'
			request.setAttribute("msg", "에러가 발생했습니다. 관리자에게 문의 해주세요");
			request.setAttribute("data", param);
			//ViewResolver.forward("user/join", request, response);
			doGet(request, response);
			return;
		}
		
		
		
		//회원가입 후 로그인 처리  
		param.setU_birth(null);
		
		result = UserDAO.selUser(param);
		
		if(result == 1) {
			HttpSession hs = request.getSession();
			hs.setAttribute(Const.LOGIN_USER,param);
			
			response.sendRedirect("/webtoon/cmt");
		} else {
			response.sendRedirect("/login");
		}
		
	}
}
