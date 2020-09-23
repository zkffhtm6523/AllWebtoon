package com.allWebtoon.crawling;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class Daum {
	public static void getCompleteDaum(ArrayList<CrawWebtoonVO> list) throws UnsupportedEncodingException, IOException {
		int cnt = 0;
		int err = 0;
		String arrURL = "http://webtoon.daum.net/data/pc/webtoon/list_finished/?genre_id=&timeStamp=1600702695173";
		URL	url = new URL(arrURL);
		//HttpURLConnection으로 형변환
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		//URL 연결
		huc.connect();
		//InputStream 스타일의 매개변수를 UTF-8 방식으로 바꾼 주소값을 isr에 넣어준다.
		InputStreamReader isr = new InputStreamReader(huc.getInputStream(), "UTF-8");
		//isr -> JSONValue로 파싱-> JSONObject로 파싱
		JSONObject object = (JSONObject) JSONValue.parse(isr);
		//data키의 value값 받아오기
		JSONArray data = (JSONArray) object.get("data");
		for (int i = 0; i < data.size(); i++) {
			///만화 중복 시 사용할 변수
			int deptStop = 0;
			
			//타이틀 가져오기
			String title = ((JSONObject)data.get(i)).get("title").toString();
			
			CrawWebtoonVO daumVo = new CrawWebtoonVO();
			System.out.println("타이틀 : "+title);
			//썸네일 가져오기
			JSONObject jobj_thumbnail = (JSONObject) ((JSONObject)data.get(i)).get("thumbnailImage2");
			String thumbnail = jobj_thumbnail.get("url").toString();
			//링크 가져오기
			String nickname = ((JSONObject)data.get(i)).get("nickname").toString().trim();
			String link = "http://webtoon.daum.net/webtoon/view/"+nickname;
			//스토리, 장르 가져오기
			
			//작가 가져오기
			JSONObject cartoon = (JSONObject) ((JSONObject)data.get(i)).get("cartoon");
			JSONArray artistArr = ((JSONArray)cartoon.get("artists"));
			ArrayList<String> deptArtist = new ArrayList<String>();
			for (int k = 0; k < artistArr.size(); k++) {
				String temp = (((JSONObject)(artistArr.get(k))).get("penName").toString());
				if(!deptArtist.contains(temp)) {
					deptArtist.add(temp);
				}
			}
			String[] writers = new String[deptArtist.size()]; 
			for (int k = 0; k < deptArtist.size(); k++) {
				writers[k] = deptArtist.get(k);
			}
				
			//VO 저장
			//스토리, 장르 담을 예정
			int result = getGenreStory(nickname, daumVo);
			if(result == 2) {err ++; continue;}
			daumVo.setTitle(title);	//타이틀
			for (String w : writers) {daumVo.setWriter(w);}	//작가 배열
//			if(genre != 0) {daumVo.setGenre(genre);} //장르
			daumVo.setThumbnail(thumbnail); //썸네일
			daumVo.setLink(link); //링크
			daumVo.setPlatform(2); //플랫폼 넘버
			//연재웹툰 갯수 세기
			cnt++;
			list.add(daumVo);
		}
		System.out.println("총 갯수 : "+cnt);
		System.out.println("에러 : "+err);
	}
	public static int getGenreStory(String nickname, CrawWebtoonVO daumVo) throws UnsupportedEncodingException, IOException {
		String arrURL = "http://webtoon.daum.net/data/pc/webtoon/view/"+nickname+"?timeStamp=1600712951497";
		URL	url = new URL(arrURL);
		//HttpURLConnection으로 형변환
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		//URL 연결
		huc.connect();
		InputStreamReader isr = new InputStreamReader(huc.getInputStream(), "UTF-8");
		JSONObject object = (JSONObject) JSONValue.parse(isr);
		JSONObject data = (JSONObject) object.get("data");
		if(data == null) {return 2;}
		JSONObject webtoon = (JSONObject) data.get("webtoon");
		String introduction = webtoon.get("introduction").toString();
		daumVo.setStory(introduction);
		
		//장르 가져오기
		JSONObject cartoon = (JSONObject)webtoon.get("cartoon");
		JSONObject zeroIndex = (JSONObject) ((JSONArray)cartoon.get("genres")).get(0);
		String strGenre = zeroIndex.get("name").toString();
		int genre = getGenreNo(strGenre);
		daumVo.setGenre(genre);
		return 1;
	}
	public static void getDaum(ArrayList<CrawWebtoonVO> list) throws UnsupportedEncodingException, IOException {
	
	String[] day = {"mon","tue","wed","thu","fri","sat","sun"};
	int cnt = 0;
	for (int i = 0; i < day.length; i++) {
		String arrURL = "http://webtoon.daum.net/data/pc/webtoon/list_serialized/"
					+day[i]+"?timeStamp=1600441691888";
		URL	url = new URL(arrURL);
		//HttpURLConnection으로 형변환
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		//URL 연결
		huc.connect();
		
		//InputStream 스타일의 매개변수를 UTF-8 방식으로 바꾼 주소값을 isr에 넣어준다.
		InputStreamReader isr = new InputStreamReader(huc.getInputStream(), "UTF-8");
		//isr -> JSONValue로 파싱-> JSONObject로 파싱
		JSONObject object = (JSONObject) JSONValue.parse(isr);
		
		//data키의 value값 받아오기
		JSONArray data = (JSONArray) object.get("data");
		
		for (int j = 0; j < data.size(); j++) {
			///만화 중복 시 사용할 변수
			int deptStop = 0;
			
			//타이틀 가져오기
			String title = ((JSONObject)data.get(j)).get("title").toString();
			//기존 배열에 담긴 타이틀하고 비교함
			for(CrawWebtoonVO vo : list) {
				if(vo.getTitle().equals(title)) { deptStop = 1;}
			}
			//같은 값이 있다면 이번 과정 전체 패스
			if(deptStop == 1) {continue;}
			
			//썸네일 가져오기
			JSONObject jobj_thumbnail = (JSONObject) ((JSONObject)data.get(j)).get("pcHomeImage");
			String thumbnail = jobj_thumbnail.get("url").toString();
			//스토리 가져오기
			String introduction = ((JSONObject)data.get(j)).get("introduction").toString();
			//링크 가져오기
			String nickname = ((JSONObject)data.get(j)).get("nickname").toString();
			String link = "http://webtoon.daum.net/webtoon/view/"+nickname;
			//장르 가져오기
			JSONObject cartoon = (JSONObject) ((JSONObject)data.get(j)).get("cartoon");
			JSONObject zeroIndex = (JSONObject) ((JSONArray)cartoon.get("genres")).get(0);
			String strGenre = (String) zeroIndex.get("name").toString();
			int genre = getGenreNo(strGenre);
			//작가 가져오기
			JSONArray artistArr = ((JSONArray)cartoon.get("artists"));
			ArrayList<String> deptArtist = new ArrayList<String>();
			for (int k = 0; k < artistArr.size(); k++) {
				String temp = (((JSONObject)(artistArr.get(k))).get("penName").toString());
				if(!deptArtist.contains(temp)) {
					deptArtist.add(temp);
				}
			}
			String[] writers = new String[deptArtist.size()]; 
			for (int k = 0; k < deptArtist.size(); k++) {
				writers[k] = deptArtist.get(k);
			}
			
				
			//VO 저장
			CrawWebtoonVO daumVo = new CrawWebtoonVO();
			daumVo.setTitle(title);	//타이틀
			for (String w : writers) {daumVo.setWriter(w);}	//작가 배열
			daumVo.setStory(introduction);	//스토리
			if(genre != 0) {daumVo.setGenre(genre);} //장르
			daumVo.setThumbnail(thumbnail); //썸네일
			daumVo.setLink(link); //링크
			daumVo.setPlatform(2); //플랫폼 넘버
			//연재웹툰 갯수 세기
			cnt++;
//			System.out.println(cnt+" : "+thumbnail);
//			System.out.println(cnt+" : "+title+"/"+introduction);
			list.add(daumVo);
		}
	}
	System.out.println("총 웹툰 갯수 : "+cnt);
		
	}
	public static int getGenreNo(String str) {
		int genre;
		switch(str) {
			case "순정":
				genre = 1; break;
			case "드라마":
				genre = 2; break;
			case "일상":
				genre = 3; break;
			case "학원":
				genre = 5; break;
			case "코믹":
				genre = 6; break;
			case "판타지":
				genre = 8; break;
			case "무협":
				genre = 9; break;
			case "액션":
				genre = 9; break;
			case "스포츠":
				genre = 12; break;
			case "미스터리":
				genre = 12; break;
			case "공포":
				genre = 13; break;
			case "스릴러":
				genre = 14; break;
			default:
				genre=0; break;
		}
		return genre;
	}
	
}
