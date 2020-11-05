package com.allWebtoon.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import com.allWebtoon.dao.WebtoonCmtDAO;
import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.util.Const;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.allWebtoon.vo.WebtoonCmtVO;
import com.allWebtoon.vo.WebtoonVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@WebServlet("/webtoon/cmt")
public class WebtoonCmtSer extends HttpServlet {
   private static final long serialVersionUID = 1L;
   
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	   HttpSession hs = request.getSession();
	   
	   UserVO loginUser = MyUtils.getLoginUser(request);
	   final int count=50;
	   
	   
		if(loginUser == null) {
			response.sendRedirect("/login");
			return;
		}
	   
	   int idx = MyUtils.getIntParameter(request, "page");
	   
	   
	   ArrayList<WebtoonVO> list = new ArrayList<WebtoonVO>();
	   ArrayList<WebtoonVO> sendarr = new ArrayList<WebtoonVO>();
	   ArrayList<WebtoonCmtVO> cmt_list = new ArrayList<WebtoonCmtVO>();
	   
		
	   cmt_list = WebtoonListDAO.selCmtList(cmt_list, loginUser.getU_no());
		
	   if(idx == 0) {
		    list = WebtoonListDAO.selRandomWebtoonList(list,0,0,"");
		    
			hs.setAttribute("rating_list",list);
			
			for(int i=0; i<count; i++) {
				sendarr.add(list.get(i));
			}
			
			request.setAttribute("list", sendarr);
			request.setAttribute("cmt_list", cmt_list);
			
			ViewResolver.viewForward("starRating", request, response);
		   
	   } else {
		   
		   list = (ArrayList<WebtoonVO>)hs.getAttribute("rating_list");
		   
		   for(int i=idx; i<(idx+count); i++){
			   sendarr.add(list.get(i));
		   }
		   
		   	Gson gson = new Gson();
			String json = gson.toJson(sendarr);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(json);
	   }
		
   }
   

   // 댓글 ( 작성 / 수정 )
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  UserVO loginUser = new UserVO();
      
      loginUser = MyUtils.getLoginUser(request);
      int u_no = loginUser.getU_no();
      String ratingPage = null;
      
	  int w_no = MyUtils.getIntParameter(request, "w_no");
	  String strC_rating = request.getParameter("c_rating");
	  String c_com = request.getParameter("c_com");
      String cmtChk = request.getParameter("cmtChk"); // 댓글 등록인지 수정인지 판단하는 변수
      
      if(strC_rating == null) {
    	  
    	  String body = IOUtils.toString(request.getReader());
    	  JsonParser parser = new JsonParser();
          JsonObject object = (JsonObject) parser.parse(body);
          
          w_no = Integer.parseInt(object.get("w_no").toString());
          strC_rating = object.get("c_rating").toString();
          ratingPage = object.get("ratingPage").toString();
          cmtChk = object.get("cmtChk").toString();
              
      }
    //  String genre_name = request.getParameter("genre_name");

      float c_rating = Float.parseFloat(strC_rating);
      
      WebtoonCmtVO param = new WebtoonCmtVO();
     // UserVO vo = new UserVO();
      
      param.setU_no(u_no);
      param.setW_no(w_no);

      if(!"".equals(c_com) && c_com!=null) {
    	  String filterCcom1 = scriptFilter(c_com);
    	  String filterCcom2 = swearWordFilter(filterCcom1);
    	  
    	  param.setC_com(filterCcom2);
      }
      param.setC_rating(c_rating);
     // vo.setU_id(loginUser.getU_id());
      
      int result;
      switch(cmtChk) {
      case "0": // 등록
         result = WebtoonCmtDAO.insCmt(param);
      //   UserDAO.insU_genre(vo, genre_name);
         //System.out.println("댓글 등록 : " + result);
         break;
      case "1": // 수정
         result = WebtoonCmtDAO.updCmt(param);
         //System.out.println("댓글 수정 : " + result);
         break;
      case "2":
    	  result = WebtoonCmtDAO.delCmt(param);
    	  //System.out.println("평점 삭제 : " + result);
    	  break;
      }
      
      if(ratingPage == null) {
    	  response.sendRedirect("/webtoon/detail?w_no=" + w_no);
      }
   }
   
   //욕 필터
 	private String swearWordFilter(final String ctnt) {
 		String[] filters = {"개새끼", "미친년", "ㄱ ㅐ ㅅ ㅐ ㄲ ㅣ"};
 		String result = ctnt;
 		for(int i=0; i<filters.length; i++) {
 			result = result.replace(filters[i], "***");
 		}
 		return result;
 	}
 	
 	//스크립트 필터
 	private String scriptFilter(final String ctnt) {
 		String[] filters = {"<script>", "</script>","\"\">"};
 		String[] filterReplaces = {"&lt;script&gt;", "&lt;/script&gt;","\"\""};
 		
 		String result = ctnt;
 		for(int i=0; i<filters.length; i++) {
 			result = result.replace(filters[i], filterReplaces[i]);
 		}
 		return result;
 	}
}