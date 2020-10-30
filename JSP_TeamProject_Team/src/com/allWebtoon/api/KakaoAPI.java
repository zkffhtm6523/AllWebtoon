package com.allWebtoon.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.allWebtoon.dao.UserDAO;
import com.allWebtoon.util.Const;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/kakaoAPI")
public class KakaoAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String access_token = getAccessToken(request.getParameter("code"));
		UserVO userInfo;
		try {
			userInfo = getUserInfo(access_token);
			int result = UserDAO.selSNSUser(userInfo);
			if(result == 0) {
				request.setAttribute("userInfo",userInfo);
				ViewResolver.accessForward("join", request, response);
				return;
			}
			//에러처리
			if(result == 2) {		
				String msg = "비밀번호가 틀렸습니다.";
				request.setAttribute("msg",msg);
			}
			request.setAttribute("u_id", userInfo.getU_name());
			HttpSession hs = request.getSession();
			hs.setAttribute(Const.LOGIN_USER,userInfo);
			
			response.sendRedirect("/");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getAccessToken(String authorize_code) {
		String access_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=48c16d63af5493c7ae43a1433ec7760f");
			//sb.append("&redirect_uri=http://allwebtoon.xyz/login?platNo=1");
			sb.append("&redirect_uri=http://localhost:8089/kakaoAPI");
			sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();
            
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {result += line;}
            
            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return access_Token;
	}
	public static UserVO getUserInfo (String access_Token) throws Exception {
	    UserVO param = new UserVO();
	    String reqURL = "https://kapi.kakao.com/v2/user/me";
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        
	        //요청에 필요한 Header에 포함될 내용
	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line = "", result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(result);
	        
	        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
	        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
	        
	        String user_id = element.getAsJsonObject().get("id").getAsString();
	        String nickname = "", gender ="", profile_image ="", email = "";

	        nickname = properties.getAsJsonObject().get("nickname").getAsString();
	        gender = kakao_account.getAsJsonObject().get("gender").getAsString();
	        email = kakao_account.getAsJsonObject().get("email").getAsString();
	        profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
	        
	        param.setU_id(user_id);
	        param.setU_password(user_id);
	        param.setU_joinPath(2);
	       
	        
	        if(!"".equals(email)) {param.setU_email(email);}
	        if(!"".equals(nickname)) {param.setU_name(nickname);}
	        if(!"".equals(profile_image)) {
	        	param.setU_profile(profile_image);
	        	param.setChkProfile(param.getU_profile().substring(0, 4));
	        }  
	        if(!"".equals(gender)) {
	        	param.setGender_name(gender.equals("male") ? "남성" : "여성");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return param;
	}
}
