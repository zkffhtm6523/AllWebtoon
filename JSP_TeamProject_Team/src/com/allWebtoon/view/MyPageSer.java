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

import org.apache.commons.io.IOUtils;

import com.allWebtoon.dao.MyPageDAO;
import com.allWebtoon.dao.WebtoonCmtDAO;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.allWebtoon.vo.WebtoonCmtDomain;
import com.allWebtoon.vo.WebtoonCmtVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/myPage")
public class MyPageSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idx = MyUtils.getIntParameter(request, "page");
		String type = request.getParameter("type");
		String yn_modal = request.getParameter("yn_modal");
		
		//System.out.println("type: " + type );
		  
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
	
		//찜한 웹툰  
		List<WebtoonCmtDomain> favoriteList = new ArrayList<WebtoonCmtDomain>();
		MyPageDAO.selfavoriteWebtoon(favoriteList, loginUser.getU_no());
		
		if(type == null) {

			List<WebtoonCmtDomain> first_cmtList = new ArrayList<WebtoonCmtDomain>();
			List<WebtoonCmtDomain> first_favoriteList = new ArrayList<WebtoonCmtDomain>();
			
			request.setAttribute("cmtlistSize", selCmtList.size());
			
			if(selCmtList.size() != 0) {
				for (int i = 0; i < (selCmtList.size() > 5 ? 5 : selCmtList.size()); i++) {
					first_cmtList.add(selCmtList.get(i));
				}
				request.setAttribute("list", first_cmtList);
			}
			if(favoriteList.size() != 0) {
				for (int i = 0; i < (favoriteList.size() > 5 ? 6 : favoriteList.size()); i++) {
					first_favoriteList.add(favoriteList.get(i));
				}
				request.setAttribute("favoritelist", first_favoriteList);
			}
			ViewResolver.accessForward("myPage", request, response);
		}
		
	/*	if(selCmtList.size() == 0 && idx == 0) {
			ViewResolver.accessForward("myPage", request, response);
			return;
		}else if(selCmtList.size() >= 1 && idx == 0) {
			System.out.println("여긴가");
			List<WebtoonCmtDomain> firstList = new ArrayList<WebtoonCmtDomain>();
			for (int i = 0; i < selCmtList.size(); i++) {
				firstList.add(selCmtList.get(i));
			}
			request.setAttribute("list", firstList);
			
			ViewResolver.accessForward("myPage", request, response);*/
		//ajax 로직
		//}
		else {
			//selCmtList = new ArrayList<WebtoonCmtDomain>();
			//MyPageDAO.myWebtoon(selCmtList, loginUser.getU_no());
			
			//System.out.println("selcmtlist : " + selCmtList.get(idx).getW_title());
			
			List<WebtoonCmtDomain> list;
			
			if("cmt".equals(type)) {
				list = selCmtList;
			}else {
				list = favoriteList;
			}
			
			Gson gson = new Gson();
			
			if(yn_modal != null) {
				
				String json = gson.toJson(list);
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.print(json);
				return ;
			}
			
			if(list.size() > idx) {
				//idx -= 1;
				
				String json = gson.toJson(list.get(idx));
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.print(json);
			}else if(list.size() <= idx) {
				PrintWriter out = response.getWriter();
				out.print("0");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int u_no = MyUtils.getLoginUserPk(request);
		String body = IOUtils.toString(request.getReader());
  	  	
		
		JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(body);
        String strW_no = object.get("w_no").toString();
        String strIdx = object.get("idx").toString();
        int w_no = MyUtils.parseStrToInt(strW_no);
        int idx = MyUtils.parseStrToInt(strIdx);
        
        WebtoonCmtVO vo = new WebtoonCmtVO();
        vo.setW_no(w_no);
        vo.setU_no(u_no);
        
        int result = WebtoonCmtDAO.delCmt(vo);
        
        if(result == 1) {
        	List<WebtoonCmtDomain> list = new ArrayList<WebtoonCmtDomain>();
        	MyPageDAO.myWebtoon(list, u_no);
        	
    		if(list.size() > idx) {
	        	Gson gson = new Gson();
	    		String json = gson.toJson(list.get(idx));
	    		response.setCharacterEncoding("UTF-8");
	    		response.setContentType("application/json");
	    		PrintWriter out = response.getWriter();
	    		out.print(json);
    		}else {
				PrintWriter out = response.getWriter();
				out.print("0");
			}
        }
	}
}