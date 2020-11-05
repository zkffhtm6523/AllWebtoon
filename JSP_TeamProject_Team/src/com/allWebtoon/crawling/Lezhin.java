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
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Lezhin {
	public static ArrayList<CrawWebtoonVO> getLezhin(ArrayList<CrawWebtoonVO> list) throws UnsupportedEncodingException, IOException {

		// 레진코믹스 연재작
		URL url = new URL(
		"https://www.lezhin.com/api/v2/inventory_groups/home_scheduled_k?platform=web&store=web&_=1597027210408");
		
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod("GET");
		//레진의 경우 언어가 영어로 설정되어 있어 헤더에 지역 정보를 포함해줘야 함.  
		huc.setRequestProperty("x-lz-locale", "ko-KR");
		huc.connect();
		
		InputStreamReader isr = new InputStreamReader(huc.getInputStream(), "UTF-8");
		JSONObject object = (JSONObject) JSONValue.parse(isr);
		
		JSONObject gata = (JSONObject) object.get("data");
		JSONArray bodyArray = (JSONArray) gata.get("inventoryList");

		// 1: 일 ~ 7: 토 , 8: 완결
		for (int i = 1; i < bodyArray.size(); i++) {
			JSONArray items = (JSONArray) (((JSONObject) bodyArray.get(i)).get("items"));
			// 요일마다 작품
			for (int z = 0; z < items.size(); z++) {
				//웹툰 중복으로 저장되는 것 방지하기 위한 변수
				boolean stop=false;
				JSONObject data = (JSONObject) items.get(z);
				//CartoonVO 객체 생성
				CrawWebtoonVO webtoonVO = new CrawWebtoonVO();
				
				//1. Title 추출
				String title = data.get("title").toString();
				
				//중복 제거 로직 (기존 list에 같은 타이틀이 있으면 건너뛰기)
				for(CrawWebtoonVO vo : list) {
					if(vo.getTitle().equals(title)) {
						stop=true;
						break;
					}
				}
				if(stop) {
					continue;
				}
				
				//작가 정보 추출
				JSONArray authors = (JSONArray) data.get("authors");
				// 장르
				JSONArray genres = (JSONArray) data.get("genres");
				// 웹툰 상세페이지 이동링크
				String link = "https://www.lezhin.com" + data.get("targetUrl").toString();
				// 링크 이용해서 상세페이지로 가서 스토리 추출
				String story = getStory(link);
				// 썸네일
				JSONArray im = (JSONArray) data.get("mediaList");
				String img = "https://cdn.lezhin.com/v2" + ((JSONObject) im.get(0)).get("url").toString();
				
				
				//VO에 작가 정보 형변환 및 저장
				for(Object a : authors) {
					webtoonVO.setWriter(((JSONObject)a).get("name").toString());
				}

				//장르 저장
				int genre_no=0;
				for(Object g : genres) {
					genre_no = getGenreNo(g.toString());
					if(genre_no != 0) {
						webtoonVO.setGenre(genre_no);
					}
				}

				//0. 플랫폼 저장(1:네이버,2:다음,3:카카오,4:레진,5:코미코)
				webtoonVO.setPlatform(4);	
				webtoonVO.setLink(link);
				webtoonVO.setStory(story);
				webtoonVO.setTitle(title);
				webtoonVO.setThumbnail(img);

				list.add(webtoonVO);
			}
		}
		return list;
	}

	// 웹툰 상세페이지로 이동해서 스토리 뽑아오는 메소드
	public static String getStory(String url) throws IOException {
		Connection conn = Jsoup.connect(url);

		Document html = conn.get();
		Element comicinfo = html.getElementsByClass("comicInfoExtend__synopsis").first();
		String story = comicinfo.getElementsByTag("p").first().text();

		return story;
	}
	
	public static int getGenreNo(String str) {
		int result;
		if(str.equals("romance") || str.equals("로맨스")) {
			result=1;
		}else if(str.equals("fantasy") || str.equals("판타지")) {
			result=8;
		}else if(str.equals("horror") || str.equals("호러")) {
			result=13;
		}else if(str.equals("sports") || str.equals("스포츠")) {
			result=11;
		}else if(str.equals("gl") || str.equals("백합")) {
			result=17;
		}else if(str.equals("historical") || str.equals("시대극")) {
			result=7;
		}else if(str.equals("bl") || str.equals("BL")) {
			result=16;
		}else if(str.equals("gore") || str.equals("스릴러")) {
			result=14;
		}else if(str.equals("girl") || str.equals("소녀만화")) {
			result=20;
		}else if(str.equals("gag") || str.equals("개그")) {
			result=6;
		}else if(str.equals("food") || str.equals("음식")) {
			result=3;
		}else if(str.equals("drama") || str.equals("드라마")) {
			result=2;
		}else if(str.equals("mystery") || str.equals("미스터리")) {
			result=12;
		}else if(str.equals("sf") || str.equals("sf")) {
			result=19;
		}else if(str.equals("martial") || str.equals("무협")) {
			result=9;
		}else if(str.equals("school") || str.equals("학원")) {
			result=5;
		}else if(str.equals("action") || str.equals("액션")) {
			result=9;
		}else if(str.equals("day") || str.equals("일상")) {
			result=3;
		}else {
			result=0;
		}
		
		return result;
	}
}
