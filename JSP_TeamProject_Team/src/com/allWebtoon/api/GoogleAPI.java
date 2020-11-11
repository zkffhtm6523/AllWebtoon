package com.allWebtoon.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.allWebtoon.dao.UserDAO;
import com.allWebtoon.util.Const;
import com.allWebtoon.util.SecurityUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/googleAPI")
public class GoogleAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Gson gson = new Gson();
		GoogleToken token = gson.fromJson(getAccessToken(request.getParameter("code")), GoogleToken.class);
		String result = getHttpConnection(token.getAccess_token());
		
		UserVO userInfo = getUserInfo(result);
		
		int db_result = UserDAO.selSNSUser(userInfo);
		
		if(db_result == 0) {
			request.setAttribute("userInfo",userInfo);
			ViewResolver.accessForward("join", request, response);
			return;
		}else if(db_result == 2) {
			String msg = "비밀번호가 틀렸습니다.";
			request.setAttribute("msg",msg);
			ViewResolver.accessForward("login", request, response);
			return;
		}
		
		HttpSession hs = request.getSession();
		hs.setAttribute(Const.LOGIN_USER,userInfo);
		
		response.sendRedirect("/");
	}
	public static UserVO getUserInfo(String result) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(result);
		String u_name = element.getAsJsonObject().get("name").getAsString();
		String u_email = element.getAsJsonObject().get("email").getAsString();
		String u_profile = element.getAsJsonObject().get("picture").getAsString();
		String u_id = element.getAsJsonObject().get("sub").getAsString();
		
		UserVO userInfo = new UserVO();
		userInfo.setU_id(u_id);
		userInfo.setU_name(u_name);
		userInfo.setU_password(u_id);
		userInfo.setU_profile(u_profile.trim());
		userInfo.setU_email(u_email);
		userInfo.setU_joinPath(4);
		userInfo.setChkProfile(userInfo.getU_profile().substring(0, 4));
		
		return userInfo;
	}
	private String getAccessToken(String code) throws ServletException, IOException {
		String reqURL = "https://www.googleapis.com/oauth2/v4/token";
		String query =	"code="+code; 
		
		query += "&"+SNSInfo.getGoogleClientId();
		query += "&"+SNSInfo.getGoogleClientSecret();
		query += "&redirect_uri=" +SNSInfo.getGoogleRedirectUri();
		query += "&grant_type=authorization_code";

		URL url = new URL(reqURL);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		try (OutputStream stream = conn.getOutputStream()) {
			try (BufferedWriter wd = new BufferedWriter(new OutputStreamWriter(stream))) {
				wd.write(query);
			}
		}
		String line;
		StringBuffer buffer = new StringBuffer();
		try (InputStream stream = conn.getInputStream()) {
			try (BufferedReader rd = new BufferedReader(new InputStreamReader(stream))) {
				while ((line = rd.readLine()) != null) {
					buffer.append(line);
					buffer.append('\r');
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	private String getHttpConnection(String token) throws ServletException, IOException {
		String uri = "https://www.googleapis.com/oauth2/v3/userinfo?alt=json&access_token="+token;
		URL url = new URL(uri);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		String line;
		StringBuffer buffer = new StringBuffer();
		try (InputStream stream = conn.getInputStream()) {
			try (BufferedReader rd = new BufferedReader(new InputStreamReader(stream))) {
				while ((line = rd.readLine()) != null) {
					buffer.append(line);
					buffer.append('\r');
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}


}
