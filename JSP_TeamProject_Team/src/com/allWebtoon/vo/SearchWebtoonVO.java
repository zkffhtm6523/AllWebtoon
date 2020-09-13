package com.allWebtoon.vo;

public class SearchWebtoonVO extends WebtoonVO {
	private String searchKeyword;
	private String w_genre_name;
	private String w_writer;
	
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getW_genre_name() {
		return w_genre_name;
	}
	public void setW_genre_name(String w_genre_name) {
		this.w_genre_name = w_genre_name;
	}
	public String getW_writer() {
		return w_writer;
	}
	public void setW_writer(String w_writer) {
		this.w_writer = w_writer;
	}
}