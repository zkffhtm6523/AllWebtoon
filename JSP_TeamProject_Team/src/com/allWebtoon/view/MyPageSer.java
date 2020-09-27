package com.allWebtoon.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allWebtoon.dao.MyPageDAO;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.allWebtoon.vo.WebtoonCmtDomain;
import com.google.gson.Gson;

@WebServlet("/myPage")
public class MyPageSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idx = MyUtils.getIntParameter(request, "page");
		//로그인 체크
		UserVO loginUser = MyUtils.getLoginUser(request);
		if(loginUser == null) {
			response.sendRedirect("/login");
			return;
		}
		List<WebtoonCmtDomain> selRecentList = new ArrayList<WebtoonCmtDomain>();
		MyPageDAO.selRecentlyWebtoon(selRecentList, loginUser.getU_no());
		if(selRecentList.size() > 0) {
			request.setAttribute("recentWebtoon", selRecentList);
		}
		//댓글 및 평점 배열
		List<WebtoonCmtDomain> selCmtList = new ArrayList<WebtoonCmtDomain>();
		MyPageDAO.myWebtoon(selCmtList, loginUser.getU_no());
		if(selCmtList.size() == 0 && idx == 0) {
			ViewResolver.accessForward("myPage", request, response);
			return;
		}else if(selCmtList.size() >= 1 && idx == 0) {
			System.out.println("여긴가");
			List<WebtoonCmtDomain> firstList = new ArrayList<WebtoonCmtDomain>();
			for (int i = 0; i < selCmtList.size(); i++) {
				firstList.add(selCmtList.get(i));
			}
			request.setAttribute("list", firstList);
			
			ViewResolver.accessForward("myPage", request, response);
		//ajax 로직
		}else {
			if(selCmtList.size() >= idx) {
				idx -= 1;
				System.out.println("ajax 왔음  ");
				Gson gson = new Gson();
				String json = gson.toJson(selCmtList.get(idx));
				System.out.println("json : " + json);
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.print(json);
			}else if(selCmtList.size() == idx || selCmtList.size() < idx) {
				PrintWriter out = response.getWriter();
				out.print("0");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
