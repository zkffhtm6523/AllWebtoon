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
	public static void getCompleteToptoon(ArrayList<CrawWebtoonVO> list) throws UnsupportedEncodingException, IOException {
		String completeUrl = "https://toptoon.com/json?fileUrl=https%3A%2F%2Ffastidious.toptoon.com%2Fproduction%2Fcomplete%2Fa282d53d7feedbb0b06876c209dede8ab412f21e660d43cf158c2cacad5d49ba.json&backupName=all";
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
		System.out.println(object);
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
		String preUrl = "https://toptoon.com/json?fileUrl=https%3A%2F%2Ffastidious.toptoon.com%2Fproduction%2F";
		String [] toptoonUrl = new String[8];
		//월요일
		toptoonUrl[0] = preUrl+"weekly%2F2a8643e666a74bfb1838f8ac76a938826983f7deb2eddffe2a83272b16069a7f.json&backupName=weekly_1";
		//화요일
		toptoonUrl[1] = preUrl+"weekly%2Fb2de74a5afa9af264a4bcc90d4a6a77db4df2097776550daee818826069e84d5.json&backupName=weekly_2";
		//수요일
		toptoonUrl[2] = preUrl+"weekly%2Fcd18663a05fa9cd6068e44eaf0af17eff5b6883c6ff2a45d41ff14f7a01a47e9.json&backupName=weekly_3";
		//목요일
		toptoonUrl[3] = preUrl+"weekly%2F65ad5cddd3b77468863ef30d66b885eeb4e58235b6627f4d10a14663d4239aca.json&backupName=weekly_4";
		//금요일
		toptoonUrl[4] = preUrl+"weekly%2F217f8d2f9569f0f08e0188254893d05239cb631ab84aac3361673d9cea74c722.json&backupName=weekly_5";
		//토요일
		toptoonUrl[5] = preUrl+"weekly%2F3ff6388ea21a0111af79b9ec8b2e85c67e7cb6ccde5419c7fccc56fb7a2b67ec.json&backupName=weekly_6";
		//일요일
		toptoonUrl[6] = preUrl+"weekly%2F74e45efca85f0d32db3d9f07a0314967c6cfafdbf9d23100a4c825fb9fad8765.json&backupName=weekly_7";
		//리메이크
		toptoonUrl[7] = preUrl+"remakeComic%2Fe678e1e958e54328d3f0915e04392f99e15bc902cba20ca44a803265d4766f59.json&backupName=weekly_1";
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
