package com.allWebtoon.vo;

public class SearchWebtoonVO extends WebtoonVO {
	private String searchKeyword;
	private String genre_name;
	private String w_writer;
	
	public String getGenre_name() {
		return genre_name;
	}
	public void setGenre_name(String genre_name) {
		this.genre_name = genre_name;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	
	public String getW_writer() {
		return w_writer;
	}
	public void setW_writer(String w_writer) {
		this.w_writer = w_writer;
	}
}