package com.allWebtoon.crawling;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

public class InsertWebtoon {
	public static void main(String[] args) throws UnsupportedEncodingException, IOException, ParseException {
		ArrayList<CrawWebtoonVO> list = new ArrayList<CrawWebtoonVO>();
		//1. 레진 데이터 DB 담기
//		list = Lezhin.getLezhin(list);
//		for (int i = 0; i < list.size(); i++) {
//			CrawWebtoonDAO.insWebtoonList(list.get(i));
//		}
//		레진 값 담기 완료(레진 웹 사이트 136개 확인 완료 및 DB 값 확인 완료)
		
		//2. 네이버 데이터 DB 담기
//		list = Naver.getNaver(list);
//		for (int i = 0; i < list.size(); i++) {
//		CrawWebtoonDAO.insWebtoonList(list.get(i));
//		}
		
		//3. 카카오 데이터 DB 담기
//		list = Kakao.getKakao();
//		for (int i = 0; i < list.size(); i++) {
//		CrawWebtoonDAO.insWebtoonList(list.get(i));
//		}
		//4-1. 다음 웹툰 연재작 DB 담기(카카오와 중복 제거되서 총 161개 중 126개 삽입)
//		Daum.getDaum(list);
//		for (int i = 0; i < list.size(); i++) {
//			CrawWebtoonDAO.insWebtoonList(list.get(i));
//		}
		//4-2. 다음 웹툰 연재작 DB 담기(카카오와 중복 제거되서 총 161개 중 126개 삽입)

//		Daum.getCompleteDaum(list);;
//		for (int i = 0; i < list.size(); i++) {
//			CrawWebtoonDAO.insWebtoonList(list.get(i));
//		}
		//삽입 과정에서 foreign key때문에 에러터지면서 중복제거 될거임
		//5-1. 탑툰 웹툰 연재작 DB 담기(총 347개 -> 243개 삽입) 
		Toptoon.getToptoon(list);
		for (int i = 0; i < list.size(); i++) {
			CrawWebtoonDAO.insWebtoonList(list.get(i));
		}
		//5-2. 탑툰 웹툰 연재작 DB 담기(총 197개 -> 131개 삽입)
		//연재작 중 완결작도 끼어있음, 탑툰에서 연재중인 웹툰 중에 완결작도 끼어있어서 이럼....
		//총 탑툰 374개(연재 + 완결)
		Toptoon.getCompleteToptoon(list);
		for (int i = 0; i < list.size(); i++) {
			CrawWebtoonDAO.insWebtoonList(list.get(i));
		}
		//5-3. 데이터베이스 t_platform의 5번 코미코 -> 탑툰으로 변경
		//update t_platform set plat_name = '탑툰' where plat_no = 5;
	}
}