package com.allWebtoon.crawling;

import java.util.ArrayList;

public class CrawWebtoonVO {
	private int w_no;
	private String thumbnail;
	private String title;
	private ArrayList<String> writer = new ArrayList<String>();
	private String story;
	private ArrayList<Integer> genre = new ArrayList<Integer>();
	private String link;
	private int platform;
	
	public int getW_no() {
		return w_no;
	}
	public void setW_no(int w_no) {
		this.w_no = w_no;
	}
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String img) {
		this.thumbnail = img;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<String> getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer.add(writer);
	}
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
	public ArrayList<Integer> getGenre() {
		return genre;
	}
	public void setGenre(int genre) {
		this.genre.add(genre);
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
