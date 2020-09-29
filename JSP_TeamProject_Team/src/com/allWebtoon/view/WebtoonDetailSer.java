package com.allWebtoon.view;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.allWebtoon.dao.WebtoonCmtDAO;
import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.allWebtoon.vo.WebtoonCmtDomain;
import com.allWebtoon.vo.WebtoonCmtVO;
import com.allWebtoon.vo.WebtoonVO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/webtoon/detail")
public class WebtoonDetailSer extends HttpServlet {
   private static final long serialVersionUID = 1L;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // 웹툰 정보 뿌리기 - 시작
      int w_no = MyUtils.getIntParameter(request, "w_no");
      int u_no = MyUtils.getLoginUserPk(request);
      System.out.println("u_no : " + u_no);
      System.out.println(w_no);
      WebtoonVO data = WebtoonListDAO.webtoonDetail(w_no, u_no);
      System.out.println("data : " + data.getIs_favorite());
      System.out.println(data.getW_title());
      String[] writer = data.getW_writer().split(", ");
      
      for(String w: writer) {
    	  System.out.println("작가 : " +w);
      }
      
      data.setW_no(w_no);
      request.setAttribute("data", data);
      request.setAttribute("writers", writer);
      // 웹툰 정보 뿌리기 - 끝
      
      // 내 댓글 뿌리기 - 시작
      UserVO loginUser = MyUtils.getLoginUser(request);
      
      if(loginUser != null) {
    	  
    	 int result =0;
    	 
    	 try {
    		 result = WebtoonListDAO.insSelWebtoon(w_no, loginUser.getU_no());
    		 System.out.println("insSelWebtoon");
    		 WebtoonListDAO.delselWebtoon(loginUser.getU_no());
    		 System.out.println("insDelWebtoon");
    	 }catch(Exception e) { }
    	 
    	 if(result == 0) {
    		 WebtoonListDAO.updSelWebtoon(w_no, loginUser.getU_no());
    	 }
    	  
         WebtoonCmtVO myCmt = new WebtoonCmtVO();
         myCmt.setW_no(w_no);
         myCmt.setU_no(loginUser.getU_no());
         WebtoonCmtVO param = WebtoonCmtDAO.selCmt(myCmt);
         System.out.println("내 댓글 : " + param.getC_com());
         System.out.println("내 별점 : " + param.getC_rating());
         request.setAttribute("myCmt", param);
      }
      // 내 댓글 뿌리기 - 끝
      
      // 다른 사람 댓글 뿌리기
      List<WebtoonCmtDomain> list = WebtoonCmtDAO.selCmtList(w_no);
      System.out.println("detail here??");
      request.setAttribute("cmtList", list); 
      ViewResolver.viewForward("webtoonDetail", request, response);
      // 다른 사람 댓글 뿌리기 끝
      // json 사용시 아래 부분이 필요한지 안한지
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String body = IOUtils.toString(request.getReader());
 	   JsonParser parser = new JsonParser();
       JsonObject object = (JsonObject) parser.parse(body);
       
       
       System.out.println("body: " + body);
       
       int w_no = Integer.parseInt(object.get("w_no").toString());
       String proc_type = object.get("proc_type").toString();
   }

}