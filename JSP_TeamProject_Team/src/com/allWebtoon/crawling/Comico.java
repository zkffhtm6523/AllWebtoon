package com.allWebtoon.crawling;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class Comico {
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		ArrayList<CrawWebtoonVO> list = new ArrayList<CrawWebtoonVO>();
		getComico(list);
	}
	
	public static void getComico(ArrayList<CrawWebtoonVO> list) throws UnsupportedEncodingException, IOException{
		
	//코미코 연재작
	URL url = new URL("https://www.comico.kr/justoon/api/comic/serial/dayofweek");
	
	HttpURLConnection huc = (HttpURLConnection)url.openConnection();
	huc.setRequestMethod("POST");
	huc.connect();
	InputStreamReader isr = new InputStreamReader(huc.getInputStream(), "UTF-8");
	JSONObject object = (JSONObject) JSONValue.parse(isr);
	
	//CartoonVO 담을 배열 생성
	
	System.out.println(object);
	
	
	}
}
