package com.allWebtoon.crawling;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Naver {
	public static ArrayList<CrawWebtoonVO> getNaver(ArrayList<CrawWebtoonVO> list) {
		// 각 url에서 상세정보 리스트로 저장
		String url = "https://comic.naver.com/webtoon/weekday.nhn";		//연재중인 웹툰
//		String url = "https://comic.naver.com/webtoon/finish.nhn";			//완결 웹툰  
		ArrayList<String> hrefList = gethref(url);
		

		try {
			for (String u : hrefList) {
				int stop=0;
				Connection conn = Jsoup.connect("https://comic.naver.com" + u);
				//기본값 초기화
				String img = "", title = "", writ = "", story = "";
				String[] genre;
				CrawWebtoonVO webtoonVO = new CrawWebtoonVO();

				Document html = conn.get();
				Element comicinfo = html.getElementsByClass("comicinfo").first();
				Element img_tag = comicinfo.getElementsByTag("img").first();
				Element detail = comicinfo.getElementsByClass("detail").first();

				img = img_tag.attr("src").toString();
				title = detail.getElementsByTag("h2").first().toString().split("<")[1].split("> ")[1];
				
				for(CrawWebtoonVO vo : list) {
					if(vo.getTitle().equals(title)) {
						stop=1;
					}
				}
				if(stop==1) {
					continue;
				}
				
				writ = detail.getElementsByClass("wrt_nm").first().text();
				String[] writers = writ.split("/ ");
				
				for(String w : writers) {
					webtoonVO.setWriter(w);
				}
			/*	if (writ.contains("/")) {
					wri_s = writ.split("/")[0];
					wri_d = writ.split("/ ")[1];
				} else {
					wri_s = writ;
					wri_d = writ;
				}*/
				story = detail.getElementsByTag("p").first().text();
				genre = detail.getElementsByClass("genre").first().text().split(", ");
				for(String g : genre) {
					int genre_no = getGenreNo(g);
					System.out.println(g.toString());
					System.out.println(genre_no);
					if(genre_no != 0) {
						webtoonVO.setGenre(genre_no);
					}
				}
				
				//webtoonVO 값 저장
				//0. 플랫폼 저장(1:네이버,2:다음,3:카카오,4:레진,5:코미코)
				webtoonVO.setPlatform(1);
				webtoonVO.setLink("https://comic.naver.com" + u);
				webtoonVO.setTitle(title);
				webtoonVO.setThumbnail(img);
				//webtoonVO.setWri_drawing(wri_d);
				//webtoonVO.setWri_story(wri_s);
				webtoonVO.setStory(story);

				//Arraylist 값 저장
				list.add(webtoonVO);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return list;
	}
	// 네이버 웹툰 메인화면에서 각 웹툰 url 추출
	public static ArrayList<String> gethref(String u) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			Connection conn = Jsoup.connect(u);
			Document html = conn.get();
			Elements list_area = html.getElementsByClass("list_area"); // class=list_area 부분 Element집합
			Elements thumb = list_area.first().getElementsByClass("thumb"); // class=thumb 부분 Element 집합

			for (Element t : thumb) {
				String href = t.getElementsByTag("a").attr("href"); // 각 a태그 href 속성값을 리스트에 저장ㅇㅇㅇ
				list.add(href);
			}
		} catch (Exception e) {}
		return list;
	}
	
	public static int getGenreNo(String str) {
		switch(str) {
		case "순정":
			return 1;
		case "로맨스":
			return 1;
		case "일상":
			return 3;
		case "개그":
			return 6;
		case "판타지":
			return 8;
		case "액션":
			return 9;
		case "드라마":
			return 2;
		case "감성":
			return 4;
		case "스릴러":
			return 14;
		case "시대극":
			return 7;
		case "스포츠":
			return 11;
		default :
			return 0;
		}
	}
}
