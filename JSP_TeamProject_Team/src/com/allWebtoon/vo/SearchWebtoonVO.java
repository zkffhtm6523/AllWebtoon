package com.allWebtoon.vo;

public class SearchWebtoonVO extends WebtoonVO {
	private String searchKeyword;
	private String w_genre;
	private String w_writer;
	
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getW_genre() {
		return w_genre;
	}
	public void setW_genre(String w_genre) {
		this.w_genre = w_genre;
	}
	public String getW_writer() {
		return w_writer;
	}
	public void setW_writer(String w_writer) {
		this.w_writer = w_writer;
	}
	
	
}