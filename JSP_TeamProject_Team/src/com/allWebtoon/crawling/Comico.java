package com.allWebtoon.crawling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class Comico {
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		ArrayList<CrawWebtoonVO> list = new ArrayList<CrawWebtoonVO>();
		System.out.println(getComico(list));
	}
	
	public static String getComico(ArrayList<CrawWebtoonVO> list) throws UnsupportedEncodingException, IOException{
		
		 URL url;
	        HttpURLConnection conn;
	        BufferedReader br;
	        BufferedWriter bw;
	        String line;
	        String result = "";
	        String parameter = String.format("page=%s&code=%s&tabType=%s&days=%s",URLEncoder.encode("webtoon","UTF-8"),
	        		URLEncoder.encode("days","UTF-8"),URLEncoder.encode("1","UTF-8"),URLEncoder.encode("127","UTF-8"));
	 
	        try {
	            url = new URL("https://www.comico.kr/comic/home?"+parameter);
	            conn = (HttpURLConnection) url.openConnection();          
	            conn.setRequestMethod("GET");
	            conn.setDoInput(true);
	            conn.setDoOutput(true);
	            conn.setInstanceFollowRedirects(false);
	            conn.setRequestProperty("Content-Type", "application/json");
	 
	            //파라메터가 없으면 지워주면 된다.
	            bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
	            bw.write(parameter);
	            //요기까지
	 
	            br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
	            while ((line = br.readLine()) != null)
	                result += line + "\n";
	            br.close();
	             
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result;
	}
}
