package com.allWebtoon.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.allWebtoon.dao.UserDAO;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/newPw")
public class NewPwSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ViewResolver.accessForward("newPw", request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String chkResult = null;
        UserVO param = new UserVO();
        
		String body = IOUtils.toString(request.getReader());
  	  	JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(body);
        
        String id = null; 
		String email = null;
		String birth = null; 
		String gender = null; 
        String pw = null;
        
        id = object.get("id").toString().split("\"")[1];
        		
        try {
    		email = object.get("email").toString().split("\"")[1];
    		birth = object.get("birth").toString().split("\"")[1];
    		gender = object.get("gender").toString().split("\"")[1];
        	
        } catch(Exception e) {}
        
        try {
        	pw = object.get("pw").toString().split("\"")[1];
        } catch(Exception e) {}
        
        
        if(pw == null) {
        	
        	param.setU_id(id);
			param.setU_email(email);
			param.setU_birth(birth);
			param.setGender_name(gender);
			
			int result = UserDAO.selUser(param);
			
			
			if(result == 2) {
				chkResult = "success";
			}else{
				chkResult = "fail";
			}
        	
        } else {
        	
        	param.setU_id(id);
			param.setU_password(pw);
			
			int result = UserDAO.updUser(param);
			
			if(result == 1) {
				chkResult = "success";
			} else {
				chkResult = "fail";
			}
        }
        
        Gson gson = new Gson();
		
		String json = gson.toJson(chkResult);
		
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(json);
        
	}

}
