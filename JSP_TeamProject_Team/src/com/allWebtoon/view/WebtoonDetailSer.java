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

import com.allWebtoon.dao.WebtoonCmtDAO;
import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.allWebtoon.vo.WebtoonCmtDomain;
import com.allWebtoon.vo.WebtoonCmtVO;
import com.allWebtoon.vo.WebtoonVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/webtoon/detail")
public class WebtoonDetailSer extends HttpServlet {
   private static final long serialVersionUID = 1L;

@SuppressWarnings("deprecation")
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // 웹툰 정보 뿌리기 - 시작
      int w_no = MyUtils.getIntParameter(request, "w_no");
      int ajaxChk = MyUtils.getIntParameter(request, "ajaxChk");
      
      if(ajaxChk == 1) {
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
    	  int loginUser_u_no = MyUtils.getLoginUserPk(request);
	      WebtoonVO data = WebtoonListDAO.webtoonDetail(w_no, loginUser_u_no);
	      
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
	      
		      // 모든 댓글 뿌리기 --------------------------------
		      List<WebtoonCmtDomain> cmtList = WebtoonCmtDAO.selCmtList(w_no);
		      List<WebtoonCmtDomain> send_cmtList = new ArrayList<WebtoonCmtDomain>();
		      float sumScore =0;					//누적 점수  
		      int numScore =0;					//평가한 사람 수   
		      for (int i = 0; i < cmtList.size(); i++) {
		    	  	sumScore += cmtList.get(i).getC_rating();
		    	  	numScore++;
		    	  	
		    	  	if(cmtList.get(i).getC_com() != null && !"".equals(cmtList.get(i).getC_com())) {		//comment가 있는 것만.
			    		String u_profile = cmtList.get(i).getU_profile();
			    		
			    		if(u_profile == null) {
			    			cmtList.get(i).setU_profile("/images/u_profile/default_image.jpg");
			    		}else if("http".equals(u_profile.substring(0, 4))){
			    			cmtList.get(i).setU_profile(u_profile);
			    		}else {
			    			int u_no = cmtList.get(i).getU_no();
			    			cmtList.get(i).setU_profile("/images/u_profile/user/"+u_no+"/"+u_profile);
			    		}
			    		System.out.println("cmtList: " + cmtList.get(i).getW_no());
			    		send_cmtList.add(cmtList.get(i));
		    	  	}
				}
		      
		      
		      System.out.println("listsize: " + cmtList.size());
		      for(WebtoonCmtDomain cmtlist: cmtList) {
		    	  System.out.println(cmtlist.getW_title());
		      }
		      	request.setAttribute("aveScore", Math.round(sumScore/(float)numScore*10)/10.0);		//평균 평점은 소수점 이하 한자리까지만.
		      	request.setAttribute("numScore", numScore);
		      	request.setAttribute("cmtList", send_cmtList); 
		      
		      // ---------------------------------------------------
	      
	      
	      
		      
		      ////파이썬호출
		      
//		     System.out.println("w_no: " + w_no);
//		     
//		    
//		     
//			 try {
//				List<WebtoonVO> list = new ArrayList<WebtoonVO>();
//				JepConfig jepConfig = new JepConfig().addSharedModules("numpy")
//	                    .addSharedModules("pandas")
//	                    .addSharedModules("scipy") 
//	                    .addSharedModules("tensorflow")
//	                    .addSharedModules("sklearn");
//	            Jep jep = new Jep(jepConfig);
//				jep.set("w_no_args", w_no);
//				//jep.runScript("E:\\python\\Python\\recommend_toon.py");
//				jep.runScript("D:\\프로그래밍\\Python\\Allwebtoon\\recommend_toon.py");
//				
//				System.out.println("rec_result: " + jep.getValue("recomment_result"));
//				
//				List arr = (List) jep.getValue("recomment_result");
//				
//				System.out.println("이 작품과 비슷한 작품 " );
//				
//				for(int i=0; i<arr.size(); i++) {
//					
//					String rec_wno = ((List) arr.get(i)).get(0).toString();
//							
//					list.add(WebtoonListDAO.selrecommendWebtoon(Integer.parseInt(rec_wno)));
//				}
//				
//				for(WebtoonVO s : list) {
//					System.out.println(s.getW_no());
//					System.out.println(s.getW_title());
//					System.out.println(s.getW_thumbnail());
//				}
//				
//				jep.close();
//				
//	
//				request.setAttribute("rec_list", list);
//				
//			 } catch (JepException e) {
//				//e.printStackTrace();
//				System.out.println("추천작품이 없습니다.");
//			 }
//		 
	     }
		 
		 
		 ViewResolver.viewForward("webtoonDetail", request, response);
		 
	    
      }
      
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String body = IOUtils.toString(request.getReader());
 	   JsonParser parser = new JsonParser();
       JsonObject object = (JsonObject) parser.parse(body);
       
       int w_no = Integer.parseInt(object.get("w_no").toString());
       String proc_type = object.get("proc_type").toString();
   }

}