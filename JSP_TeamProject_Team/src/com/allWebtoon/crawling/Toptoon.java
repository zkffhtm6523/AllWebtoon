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

public class Toptoon {
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		ArrayList<CrawWebtoonVO> list = new ArrayList<CrawWebtoonVO>();
//		getToptoon(list);
		getCompleteToptoon(list);
	}
	public static void getCompleteToptoon(ArrayList<CrawWebtoonVO> list) throws UnsupportedEncodingException, IOException {
		String completeUrl = "https://toptoon.com/json?fileUrl=https%3A%2F%2Ffastidious.toptoon.com%2Fproduction%2"
				+ "	Fcomplete%2Fbc3907214aefb20d94a66f932e290f247eb83023165671a43ce36e08390cc2de.json&backupName=all";
		String [] adult = {"non_adult"};
		int cnt  = 0;
		URL	url = new URL(completeUrl);
		//HttpURLConnection으로 형변환
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		//URL 연결
		huc.connect();
		
		//InputStream 스타일의 매개변수를 UTF-8 방식으로 바꾼 주소값을 isr에 넣어준다.
		InputStreamReader isr = new InputStreamReader(huc.getInputStream(), "UTF-8");
		//isr -> JSONValue로 파싱-> JSONObject로 파싱
		JSONObject object = (JSONObject) JSONValue.parse(isr);
		for (int j = 0; j < adult.length; j++) {
			JSONArray data = (JSONArray) object.get(adult[j]);
			//리메이크작 adult가 없을 때 패스
			if(data == null) {System.out.println("리메이크작 진입");continue;}
			//adult || non-adult 안의 배열
			for (int k = 0; k < data.size(); k++) {
				//링크 가져오기
				String id = ((JSONObject)data.get(k)).get("id").toString().trim();
				String link = "https://toptoon.com/weekly/ep_list/"+id;
				//썸네일 thumbnailNonAdult 의 wide 가져오기
				JSONObject thumbnailNonAdult = (JSONObject)((JSONObject)data.get(k)).get("thumbnailNonAdult");
				String thumbnail = thumbnailNonAdult.get("square").toString().trim();
				if(thumbnail == null || thumbnail.equals("")) {
					thumbnail = thumbnailNonAdult.get("portrait").toString().trim();
					if (thumbnail == null || thumbnail.equals("")) {
						thumbnail = thumbnailNonAdult.get("wide").toString().trim();
						if(thumbnail == null || thumbnail.equals("")) {
							thumbnail = thumbnailNonAdult.get("landscape").toString().trim();
						}
					}
				}
				//meta 안(스토리, 타이틀, 작가, 장르)
				JSONObject meta = (JSONObject)((JSONObject)data.get(k)).get("meta");
				//스토리
				String story = meta.get("description").toString().trim();
				//타이틀
				String title = meta.get("title").toString().trim();
				//작가
				JSONObject author = (JSONObject) meta.get("author");
				JSONArray authorData = (JSONArray) author.get("authorData");
				String[] writers = new String[authorData.size()];
				for (int l = 0; l < authorData.size(); l++) {
					String writer = ((JSONObject) authorData.get(l)).get("name").toString().trim();
					writers[l] = writer;
				}
				//장르
				JSONArray genreArr = (JSONArray)meta.get("genre");
				String zeroIndex = null;
				if(genreArr.size() == 0) {
					zeroIndex = "드라마";
				}else {
					zeroIndex = ((JSONObject) genreArr.get(0)).get("name").toString().trim();
				}
				int genre = getGenreNo(zeroIndex);
				System.out.println(j+" : "+k+" : 장르번호 : "+genre);
				
				CrawWebtoonVO toptoonVo = new CrawWebtoonVO();
				
				toptoonVo.setTitle(title);
				for (String w : writers) {toptoonVo.setWriter(w);}
				toptoonVo.setStory(story);
				toptoonVo.setGenre(genre);
				toptoonVo.setLink(link);
				toptoonVo.setThumbnail(thumbnail);
				toptoonVo.setPlatform(5);
				
				list.add(toptoonVo);
				cnt++;
			}
		}
		System.out.println("총 웹툰 갯수 : "+cnt);
	}
	public static void getToptoon(ArrayList<CrawWebtoonVO> list) throws UnsupportedEncodingException, IOException {
		String preUrl = "https://toptoon.com/json?fileUrl=https%3A%2F%2Ffastidious.toptoon.com%2Fproduction%2";
		String [] toptoonUrl = new String[8];
		//월요일
		toptoonUrl[0] = preUrl+"Fweekly%2F76fb864a51bef57d205a0e2ba1115430db68711ca73d45aa32855d013fc143b4.json&backupName=weekly_1";
		//화요일
		toptoonUrl[1] = preUrl+"Fweekly%2F0fc966787d5448e07c3a71d9d5284b35e83ac39f0d973d48f33672e2349c1690.json&backupName=weekly_2";
		//수요일
		toptoonUrl[2] = preUrl+"Fweekly%2Fe34d7445aa19b3fd1834501e629a925ff39ee5a04504430f410a10b2d8878f63.json&backupName=weekly_3";
		//목요일
		toptoonUrl[3] = preUrl+"Fweekly%2F416c40770ae7056571d7441e30e216b03aa8529a6eaf6b4243410da29ae98d89.json&backupName=weekly_4";
		//금요일
		toptoonUrl[4] = preUrl+"Fweekly%2F6e13b584a778ed54f64bd5accda22b74713d7cdc61f2cf2765f0fed48140b17b.json&backupName=weekly_5";
		//토요일
		toptoonUrl[5] = preUrl+"Fweekly%2Fcfaa0f72b64f5b2b53f6273af0458d39bf6841e45dc2e970f4cd89e368fe1945.json&backupName=weekly_6";
		//일요일
		toptoonUrl[6] = preUrl+"Fweekly%2Fd54f3df8618943aa605a1c6384b292eb3b4012e4850c89b7c8d4c9180221f557.json&backupName=weekly_7";
		//리메이크
		toptoonUrl[7] = preUrl+"FremakeComic%2F052dd5f7dca08333345d23c4c00e0cc4d8208695d516cb6a531686cbcfa79aab.json&backupName=weekly_1";
		String [] adult = {"non_adult"};
		int cnt  = 0;
		for (int i = 0; i < toptoonUrl.length; i++) {
			String arrUrl = toptoonUrl[i];
			URL	url = new URL(arrUrl);
			//HttpURLConnection으로 형변환
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			//URL 연결
			huc.connect();
			
			//InputStream 스타일의 매개변수를 UTF-8 방식으로 바꾼 주소값을 isr에 넣어준다.
			InputStreamReader isr = new InputStreamReader(huc.getInputStream(), "UTF-8");
			//isr -> JSONValue로 파싱-> JSONObject로 파싱
			JSONObject object = (JSONObject) JSONValue.parse(isr);
			
			for (int j = 0; j < adult.length; j++) {
				JSONArray data = (JSONArray) object.get(adult[j]);
				//리메이크작 adult가 없을 때 패스
				if(data == null) {System.out.println("리메이크작 진입");continue;}
				//adult || non-adult 안의 배열
				for (int k = 0; k < data.size(); k++) {
					//링크 가져오기
					String id = ((JSONObject)data.get(k)).get("id").toString().trim();
					String link = "https://toptoon.com/weekly/ep_list/"+id;
					//썸네일 thumbnailNonAdult 의 wide 가져오기
					JSONObject thumbnailNonAdult = (JSONObject)((JSONObject)data.get(k)).get("thumbnailNonAdult");
					String thumbnail = thumbnailNonAdult.get("square").toString().trim();
					if(thumbnail == null || thumbnail.equals("")) {
						thumbnail = thumbnailNonAdult.get("portrait").toString().trim();
						if (thumbnail == null || thumbnail.equals("")) {
							thumbnail = thumbnailNonAdult.get("wide").toString().trim();
							if(thumbnail == null || thumbnail.equals("")) {
								thumbnail = thumbnailNonAdult.get("landscape").toString().trim();
							}
						}
					}
					//meta 안(스토리, 타이틀, 작가, 장르)
					JSONObject meta = (JSONObject)((JSONObject)data.get(k)).get("meta");
					//스토리
					String story = meta.get("description").toString().trim();
					//타이틀
					String title = meta.get("title").toString().trim();
					//작가
					JSONObject author = (JSONObject) meta.get("author");
					JSONArray authorData = (JSONArray) author.get("authorData");
					String[] writers = new String[authorData.size()];
					for (int l = 0; l < authorData.size(); l++) {
						String writer = ((JSONObject) authorData.get(l)).get("name").toString().trim();
						writers[l] = writer;
					}
					//장르
					JSONArray genreArr = (JSONArray)meta.get("genre");
					String zeroIndex = null;
					if(genreArr.size() == 0) {
						zeroIndex = "드라마";
					}else {
						zeroIndex = ((JSONObject) genreArr.get(0)).get("name").toString().trim();
					}
					int genre = getGenreNo(zeroIndex);
					System.out.println(i+" : "+j+" : "+k+" : 장르번호 : "+genre);
					
					CrawWebtoonVO toptoonVo = new CrawWebtoonVO();
					
					toptoonVo.setTitle(title);
					for (String w : writers) {toptoonVo.setWriter(w);}
					toptoonVo.setStory(story);
					toptoonVo.setGenre(genre);
					toptoonVo.setLink(link);
					toptoonVo.setThumbnail(thumbnail);
					toptoonVo.setPlatform(5);
					
					list.add(toptoonVo);
					cnt++;
				}
				
			}
		}
		System.out.println("총 웹툰 갯수 : "+cnt);
	}
	
	public static int getGenreNo(String str) {
		int genre;
		switch(str) {
			case "로맨스":
				genre = 1; break;
			case "드라마":
				genre = 2; break;
			case "일상":
				genre = 3; break;
			case "옴니버스":
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
			case "공포,스릴러":
				genre = 13; break;
			case "스릴러":
				genre = 14; break;
			case "TL":
				genre = 20; break;
			default:
				genre=0; break;
		}
		return genre;
	}
}
