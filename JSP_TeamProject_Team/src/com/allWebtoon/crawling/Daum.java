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
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		ArrayList<CrawWebtoonVO> list = new ArrayList<CrawWebtoonVO>();
		getDaum(list);
	}
	@SuppressWarnings("unchecked")
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
			//썸네일 가져오기
			JSONObject jobj_thumbnail = (JSONObject) ((JSONObject)data.get(j)).get("pcHomeImage");
			String thumbnail = jobj_thumbnail.get("url").toString();
			//타이틀 가져오기
			String title = ((JSONObject)data.get(j)).get("title").toString();
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
			JSONArray artistName = new JSONArray();
			for (int k = 0; k < artistArr.size(); k++) {
			}
			JSONArray deptArtistArr = new JSONArray();
				
//				if(!deptArtistArr.contains(artistArr.get(k))) {
//					deptArtistArr.add(artistArr.get(k));
//				}
				System.out.println(deptArtistArr);
			//VO 저장
			CrawWebtoonVO daumVo = new CrawWebtoonVO();
			daumVo.setThumbnail(thumbnail);
			daumVo.setTitle(title);
			daumVo.setStory(introduction);
			daumVo.setLink(link);
			daumVo.setGenre(genre);
			daumVo.setPlatform(2);
			//연재웹툰 갯수 세기
			cnt++;
//			System.out.println(cnt+" : "+thumbnail);
//			System.out.println(cnt+" : "+title+"/"+introduction);
			list.add(daumVo);
		}
	}
		
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
				System.out.println(str);
				genre=0; break;
		}
		return genre;
	}
	
}
