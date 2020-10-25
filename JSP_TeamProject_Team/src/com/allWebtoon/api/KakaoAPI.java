package com.allWebtoon.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.allWebtoon.util.SecurityUtils;
import com.allWebtoon.vo.UserVO;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class KakaoAPI {
	public static String getAccessToken(String authorize_code) {
		String access_Token = "";
		String refresh_Token = "";
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
			sb.append("&redirect_uri=http://101.101.219.238:8080/login?platNo=1");
			//sb.append("&redirect_uri=http://localhost:8090/login?platNo=1");
			sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();
            
            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
 
            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            
            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            
            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);
            
            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        return access_Token;
    }
	public static UserVO getUserInfo (String access_Token) {
	    UserVO param = new UserVO();
	    //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
	    HashMap<String, Object> userInfo = new HashMap<>();
	    String reqURL = "https://kapi.kakao.com/v2/user/me";
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        
	        //    요청에 필요한 Header에 포함될 내용
	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
	        
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line = "";
	        String result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println("response body : " + result);
	        
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(result);
	        
	        System.out.println(element);
	        
	        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
	        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
	        
	        String user_id = element.getAsJsonObject().get("id").getAsString();
	        String nickname = "";
	        String gender ="";
	        String profile_image ="";
	        String email = "";
	        try {
	        	nickname = properties.getAsJsonObject().get("nickname").getAsString();
	        }catch(Exception e) {}
	        try {
	        	gender = kakao_account.getAsJsonObject().get("gender").getAsString();
	        }catch(Exception e) {}
	        try {
	        email = kakao_account.getAsJsonObject().get("email").getAsString();
	        }catch(Exception e) {}
	        try {
	        profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
	        } catch(Exception e) {}
	        //String thumbnail_image = properties.getAsJsonObject().get("thumbnail_image").getAsString();
	        
	        param.setU_id(user_id);
	        param.setU_password(user_id);
	        param.setU_joinPath(2);
	       
	        
	        if(!"".equals(email)) {
	        	param.setU_email(email);
	        }
	        if(!"".equals(nickname)) {
	        	param.setU_name(nickname);
	        }
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