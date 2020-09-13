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

import com.google.gson.Gson;

@WebServlet("/googleAPI")
public class GoogleAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String clientId = "659641044041-d8d9d26ubldu5veldv2g3cqaqedv6htq.apps.googleusercontent.com";
		String clientSecret = "LxGdpTGyFqWFj3AT1167xbvF";
		String code = request.getParameter("code");
		String redirectURI = "http://localhost:8089/googleAPI";
		String reqURL = "https://oauth2.googleapis.com/token";
		
		String query =	"code="+code; 
		query += "&client_id="+clientId;
		query += "&client_secret=" +clientSecret;
		query += "&redirect_uri=" +redirectURI;
		query += "&grant_type=authorization_code";

		//매개변수 2개 메소드 사용
		String tokenJson = getHttpConnection(reqURL, query);
		System.out.println(tokenJson.toString());
		Gson gson = new Gson();
		Token token = gson.fromJson(tokenJson, Token.class);
		//매개변수 1개 메소드 사용
		String ret = getHttpConnection(
				"https://www.googleapis.com/oauth2/v3/userinfo?alt=json&access_token=" + token.getAccess_token());
		//test 1
		System.out.println("ret : "+ret);
	}
		
	private String getHttpConnection(String uri) throws ServletException, IOException {
		URL url = new URL(uri);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		int responseCode = conn.getResponseCode();
		//test 2
		System.out.println("responseCode : "+responseCode);
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

	private String getHttpConnection(String uri, String param) throws ServletException, IOException {
		URL url = new URL(uri);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		try (OutputStream stream = conn.getOutputStream()) {
			try (BufferedWriter wd = new BufferedWriter(new OutputStreamWriter(stream))) {
				wd.write(param);
			}
		}
		int responseCode = conn.getResponseCode();
		System.out.println(responseCode);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
