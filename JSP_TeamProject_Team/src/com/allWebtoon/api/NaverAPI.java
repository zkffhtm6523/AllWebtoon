package com.allWebtoon.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.allWebtoon.dao.UserDAO;
import com.allWebtoon.util.Const;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/naverAPI")
public class NaverAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clientId = "gtb_8Ij5V31vLTCJA7F3";
		String clientSecret = "8dYiJWFqmT"; 
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String redirectURI = URLEncoder.encode("http://127.0.0.1:8089/naverAPI","UTF-8");
				
		StringBuffer apiURL = new StringBuffer();
		apiURL.append("https://nid.naver.com/oauth2.0/token?grant_type=authorization_code");
		apiURL.append("&client_id=" + clientId);
		apiURL.append("&client_secret=" + clientSecret);
		apiURL.append("&redirect_uri=" + redirectURI);
		apiURL.append("&code=" + code);
		apiURL.append("&state=" + state);
		String access_token = "";
		String refresh_token = ""; //나중에 이용합시다
				
		try { 
			  URL url = new URL(apiURL.toString());
		      HttpURLConnection con = (HttpURLConnection)url.openConnection();
		      con.setRequestMethod("GET");
		      int responseCode = con.getResponseCode();
		      BufferedReader br;
		      
		      if(responseCode==200) {
		    	System.out.println("정상 호출");
		        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		      } else {  // 에러 발생
		    	System.out.println("에러 호출");
		        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		      }
		      
		      String inputLine;
		      StringBuffer res = new StringBuffer();
		      
		      while ((inputLine = br.readLine()) != null) {
		        res.append(inputLine);
		      }
		      br.close();
		      if(responseCode==200) {
	    		JSONParser parsing = new JSONParser();
	    		Object obj = parsing.parse(res.toString());
	    		JSONObject jsonObj = (JSONObject)obj;
	    			        
	    		access_token = (String)jsonObj.get("access_token");
	    		refresh_token = (String)jsonObj.get("refresh_token");
		      }
		    } catch (Exception e) {
		      System.out.println(e);
		    }
		if(access_token != null) { // access_token을 잘 받아왔다면
			try {
				String apiurl = "https://openapi.naver.com/v1/nid/me";
				URL url = new URL(apiurl);
				HttpURLConnection con = (HttpURLConnection)url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("Authorization", "Bearer " + access_token);
				
				int responseCode = con.getResponseCode();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				
				String line = "";
		        String result = "";
		        
		        while ((line = br.readLine()) != null) {
		            result += line;
		        }
		        
				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(result);
				
				JsonObject property = element.getAsJsonObject().get("response").getAsJsonObject();
				System.out.println("properties : "+property);
				System.out.println(1);
				String user_id = property.getAsJsonObject().get("id").getAsString();
				System.out.println(2);
				String profile_img = property.getAsJsonObject().get("profile_image").getAsString();
				System.out.println(3);
				String gender = property.getAsJsonObject().get("gender").getAsString();
				String email = property.getAsJsonObject().get("email").getAsString();
				String name = property.getAsJsonObject().get("name").getAsString();
				String birthday = property.getAsJsonObject().get("birthday").getAsString();
				
				UserVO userInfo = new UserVO();
				userInfo.setUser_id(user_id);
				userInfo.setUser_password(user_id);
				userInfo.setName(name);
				userInfo.setBirth("1990/"+birthday.replace("-", "/"));
				userInfo.setProfile(profile_img);
				userInfo.setEmail(email);
				userInfo.setGender(gender.equals("M") ? "2" : "1");
				
				int db_result = UserDAO.selNaverUser(userInfo);
				
				if(db_result == 0) {
					UserDAO.insUser(userInfo);
					response.sendRedirect("/choGenre?user_id="+userInfo.getUser_id());
					return;
				}else if(db_result == 2) {
					String msg = "비밀번호가 틀렸습니다.";
					request.setAttribute("msg",msg);
					request.setAttribute("user_id", userInfo.getName());
					ViewResolver.accessForward("login", request, response);
					return;
				}
				
				HttpSession hs = request.getSession();
				hs.setAttribute(Const.LOGIN_USER,userInfo);
				
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		}	
		response.sendRedirect("/home");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
