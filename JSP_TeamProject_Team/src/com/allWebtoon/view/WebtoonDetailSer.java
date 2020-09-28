package com.allWebtoon.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allWebtoon.dao.WebtoonCmtDAO;
import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.allWebtoon.vo.WebtoonCmtDomain;
import com.allWebtoon.vo.WebtoonCmtVO;
import com.allWebtoon.vo.WebtoonVO;
import com.google.gson.Gson;

@WebServlet("/webtoon/detail")
public class WebtoonDetailSer extends HttpServlet {
   private static final long serialVersionUID = 1L;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // 웹툰 정보 뿌리기 - 시작
      int w_no = MyUtils.getIntParameter(request, "w_no");
      int ajaxChk = MyUtils.getIntParameter(request, "ajaxChk");
      
      if(ajaxChk == 1) {
    	System.out.println("ajaxChk : "+ajaxChk);
    	List<WebtoonCmtDomain> cmtList = WebtoonCmtDAO.selCmtList(w_no);
    	for (int i = 0; i < cmtList.size(); i++) {
    		String u_profile = cmtList.get(i).getU_profile();
    		if(u_profile == null) {
    			cmtList.get(i).setU_profile("/images/u_profile/default_image.jpg");
    		}else if("http".equals(u_profile.substring(0, 4))){
    			cmtList.get(i).setU_profile(u_profile);
    		}else {
    			int u_no = cmtList.get(i).getU_no();
    			cmtList.get(i).setU_profile("/images/u_profile/user/"+u_no+"/"+u_profile);
    		}
		}
    	Gson gson = new Gson();
    	String json = gson.toJson(cmtList);
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(json);
      }else {
    	  System.out.println("ajaxChk : "+ajaxChk);
	      WebtoonVO data = WebtoonListDAO.webtoonDetail(w_no);
	      
	      String[] writer = data.getW_writer().split(", ");
	      
	      data.setW_no(w_no);
	      request.setAttribute("data", data);
	      request.setAttribute("writers", writer);
	      // 웹툰 정보 뿌리기 - 끝
	      
	      UserVO loginUser = MyUtils.getLoginUser(request);
	      
	      if(loginUser != null) {
	    	      // 최근 조회 목록 로직--------------------------
	    	 int result = 0;
	    	 try {
	    		 	//최근 조회 웹툰 있으면 result = 1이 들어가지고
	    		 result = WebtoonListDAO.insSelWebtoon(w_no, loginUser.getU_no());
	    		 	//최근 조회 웹툰 5개 리스트 갱신
	    		 WebtoonListDAO.delselWebtoon(loginUser.getU_no());
	    	 }catch(Exception e) { }
	    	 	 	//만약 기존 웹툰 r_dt가 있으면 r_dt를 수정하는 것
		    	 if(result == 0) {WebtoonListDAO.updSelWebtoon(w_no, loginUser.getU_no());}
		    	 // ---------------------------------------
		    	 // input 박스에 남는 내가 쓴 글 뿌리기------------------------------
		         WebtoonCmtVO myCmt = new WebtoonCmtVO();
		         myCmt.setW_no(w_no);
		         myCmt.setU_no(loginUser.getU_no());
		         WebtoonCmtVO param = WebtoonCmtDAO.selCmt(myCmt);
		         
		         request.setAttribute("myCmt", param);
		         // ---------------------------------------
	      }
	      // 모든 댓글 뿌리기 --------------------------------
	      List<WebtoonCmtDomain> cmtList = WebtoonCmtDAO.selCmtList(w_no);
	      for (int i = 0; i < cmtList.size(); i++) {
	    		String u_profile = cmtList.get(i).getU_profile();
	    		if(u_profile == null) {
	    			cmtList.get(i).setU_profile("/images/u_profile/default_image.jpg");
	    		}else if("http".equals(u_profile.substring(0, 4))){
	    			cmtList.get(i).setU_profile(u_profile);
	    		}else {
	    			int u_no = cmtList.get(i).getU_no();
	    			cmtList.get(i).setU_profile("/images/u_profile/user/"+u_no+"/"+u_profile);
	    		}
			}
	      request.setAttribute("cmtList", cmtList); 
	      ViewResolver.viewForward("webtoonDetail", request, response);
	      // ---------------------------------------------------
      }
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   
   }

}