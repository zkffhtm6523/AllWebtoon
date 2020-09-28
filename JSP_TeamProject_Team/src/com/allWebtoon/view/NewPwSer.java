package com.allWebtoon.view;

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

import com.allWebtoon.dao.UserDAO;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.google.gson.Gson;

@WebServlet("/newPw")
public class NewPwSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ViewResolver.accessForward("newPw", request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String chkResult = null;
		String reqStr = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		UserVO param = new UserVO();

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
			
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		reqStr = stringBuilder.toString();
		
		if(!reqStr.contains("pw")) {
			
			String[] str = reqStr.split("\"");
			
			String id = str[3].toString();
			String email = str[7].toString();
			String birth = str[11].toString();
			String gender = str[15].toString();
			
			
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
			String[] str = reqStr.split("\"");
			
			String id = str[3].toString();
			String pw = str[7].toString();
			
			
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
