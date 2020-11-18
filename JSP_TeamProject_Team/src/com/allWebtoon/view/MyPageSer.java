package com.allWebtoon.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

import com.allWebtoon.dao.MyPageDAO;
import com.allWebtoon.dao.WebtoonCmtDAO;
import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.recommend.Mahout_Recommend;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.allWebtoon.vo.WebtoonCmtDomain;
import com.allWebtoon.vo.WebtoonCmtVO;
import com.allWebtoon.vo.WebtoonVO;
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
		//로그인 체크
		UserVO loginUser = MyUtils.getLoginUser(request);
		if(loginUser == null) {
			response.sendRedirect("/login");
			return;
		}
		//최근 조회 웹툰
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

		//추천 로직 만들기
     	GenericDataModel model =  Mahout_Recommend.parsingDataModel(WebtoonListDAO.selDataModel());
      	List<WebtoonVO> recomList = Mahout_Recommend.getUserBasedRecommend(model, loginUser.getU_no(), 5);
      	if(recomList.size() > 0) {
      		request.setAttribute("recommendlist", recomList);
      	}
      	
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
		else {
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