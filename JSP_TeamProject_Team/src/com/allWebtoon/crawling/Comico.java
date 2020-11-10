package com.allWebtoon.crawling;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Comico {
	private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
	public static void main(String[] args) {
	}
//	public static void setSSL() throws NoSuchAlgorithmException, KeyManagementException {
//		TrustManager[] trustAllCerts = new TrustManager[] {
//				new X509TrustManager() {
//					@Override
//					public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {}
//					@Override
//					public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {}
//					@Override
//					public X509Certificate[] getAcceptedIssuers() {return null;}
//				} 
//		};
//		SSLContext sc = SSLContext.getInstance("SSL");
//		sc.init(null, trustAllCerts, new SecureRandom());
//		HttpsURLConnection.setDefaultHostnameVerifier(null);
//		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//	}
//	public static void getComico() throws UnsupportedEncodingException, IOException {
//		try {
//			// 1. URL 선언
//			String connUrl = "https://www.daum.net";
//			// 2. SSL 체크
//			if (connUrl.indexOf("https://") >= 0) {
//				Comico.setSSL();
//			} // 3. HTML 가져오기
//			Connection conn = Jsoup.connect(connUrl).header("Content-Type", "application/json;charset=UTF-8")
//					.userAgent(USER_AGENT).method(Connection.Method.GET).ignoreContentType(true);
//			Document doc = conn.get();
//			// 4. 가져온 HTML Document 를 확인하기
//			System.out.println(doc.toString());
//		} catch (IOException e) {
//			// Exp : Connection Fail
//			e.printStackTrace();
//		} catch (KeyManagementException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
//JSONObject json = new JSONObject();
//JSONArray jArr = new JSONArray();
//JSONObject json2 = new JSONObject();
//
//json.put("pageNo", "1");
//json.put("rowsPerPage", "20");
//
//json2.put("serialDay", "1");
//json2.put("sortType", "0");
//
//jArr.put(json2);
//json.put("criteria", jArr);
//
//String body = json.toString();
//System.out.println(body);
////URL postUrl = new URL("https://www.comico.kr/justoon/api/comic/serial/dayofweek");
////HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
////connection.setDoOutput(true); 				// xml내용을 전달하기 위해서 출력 스트림을 사용
////connection.setInstanceFollowRedirects(false);  //Redirect처리 하지 않음
////connection.setRequestMethod("POST");
////connection.setRequestProperty("Content-Type", "application/json");
////OutputStream os= connection.getOutputStream();
////os.write(body.getBytes());
////os.flush();
////System.out.println("Location: " + connection.getHeaderField("Location"));
////
////  BufferedReader br = new BufferedReader(new InputStreamReader(
////		  (connection.getInputStream())));
////		   
////		  String output;
////		  System.out.println("Output from Server .... \n");
////		  while ((output = br.readLine()) != null) {
////		  System.out.println(output);
////		  }
////connection.disconnect();
//URL url = new URL("https://www.comico.kr/justoon/api/comic/serial/dayofweek");
//HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
//con.setRequestMethod("POST"); // HTTP POST 메소드 설정
//con.setRequestProperty("User-Agent", "Mozilla/5.0");
//con.setDoOutput(true); // POST 파라미터 전달을 위한 설정 // Send post request
//DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//wr.writeBytes(body);
//wr.flush(); wr.close();
//int responseCode = con.getResponseCode();
//BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//String inputLine;
//StringBuffer response = new StringBuffer();
//while ((inputLine = in.readLine()) != null) { 
//	response.append(inputLine);
//} 
//in.close(); // print result
//System.out.println("HTTP 응답 코드 : " + responseCode);
//System.out.println("HTTP body : " + response.toString());