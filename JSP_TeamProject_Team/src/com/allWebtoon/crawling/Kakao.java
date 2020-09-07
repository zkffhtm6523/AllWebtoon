package com.allWebtoon.crawling;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Kakao {
	public static ArrayList<CrawWebtoonVO> getKakao() throws IOException, ParseException {

		// 0~7 : 요일, 12: 완결
		String[] day = { "1", "2", "3", "4", "5", "6", "7", "12" };
		// P: 무료 W: 시간 지나면 무료
		String[] bm = { "P", "W" };
		ArrayList<CrawWebtoonVO> list = new ArrayList<CrawWebtoonVO>();

		for (int i = 0; i < day.length; i++) {
			for (int j = 0; j < bm.length; j++) {
				// z: 스크롤 내리면 나타나는 페이지 수
				for (int z = 0; z < 10; z++) {
					try {
						String u = "https://api2-page.kakao.com/api/v2/store/day_of_week_top/list?category=10&subcategory=0&page="
								+ z + "&day=" + day[i] + "&bm=" + bm[j];
						URL url = new URL(u);
						

						HttpURLConnection huc = (HttpURLConnection) url.openConnection();
						huc.connect();
						InputStreamReader isr = new InputStreamReader(huc.getInputStream(), "UTF-8");
						JSONObject object = (JSONObject) JSONValue.parse(isr);

						JSONArray bodyArray = (JSONArray) object.get("list");

						for (int b = 0; b < bodyArray.size(); b++) {
							JSONObject body = (JSONObject) bodyArray.get(b);
							CrawWebtoonVO c = new CrawWebtoonVO();
							int stop=0;
							
							String title = body.get("title").toString();
							
							//이미 제목이 같은 객체가 있을 경우 건너뜀.
							for(CrawWebtoonVO vo : list) {
								if(vo.getTitle().equals(title)) {
									stop =1;
								}
							}
							if(stop==1) {
								continue;
							}
							String img = "https://dn-img-page.kakao.com/download/resource?kid="
									+ body.get("square_thumbnail_url").toString();
							String link = "https://page.kakao.com/home?seriesId=" + body.get("series_id");
							String g = body.get("sub_category_title").toString().split("만화")[0];
							int genre=0;

							// 작가, 스토리는 상세페이지에서
							JSONObject ect = getStory(link);
							String story = ect.get("description").toString();
							String[] writers = ect.get("authorName").toString().split(",");
							
							c.setTitle(title);
							for (String w : writers) {
								c.setWriter(w);
							}
							c.setStory(story);
							
							genre=getGenreNo(g);
							System.out.println(g);
							System.out.println(genre);
							if(genre != 0) {
								c.setGenre(genre);
							}
							c.setThumbnail(img);
							c.setLink(link);
							c.setPlatform(3);

							list.add(c);
						}
					} catch (Exception e) {
						break;
					}
				}
			}
		}
		return list;
	}

	// 웹툰 상세페이지로 이동해서 새로운 JSON 객체 리턴
	public static JSONObject getStory(String url) throws IOException {
		String ur = url;

		Connection conn = Jsoup.connect(ur);

		Document html = conn.get();

		String j = html.getElementById("__NEXT_DATA__").toString();
		String json = j.split("json\">")[1];
		json = json.split("</script>")[0];

		JSONParser parser = new JSONParser();
		JSONObject object;
		JSONObject series = null;

		try {
			object = (JSONObject) parser.parse(json);
			JSONObject bodyArray = (JSONObject) object.get("props");

			JSONObject init = (JSONObject) bodyArray.get("initialState");
			JSONObject se = (JSONObject) init.get("series");
			series = (JSONObject) se.get("series");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return series;
	}
	
	public static int getGenreNo(String str) {
		int genre;
		switch(str) {
			case "순정":
				genre = 1; break;
			case "소년":
				genre = 10; break;
			case "드라마":
				genre = 2; break;
			case "BL":
				genre = 16; break;
			case "액션무협":
				genre = 9; break;
			default:
				genre=0; break;
		}
		return genre;
	}

}
