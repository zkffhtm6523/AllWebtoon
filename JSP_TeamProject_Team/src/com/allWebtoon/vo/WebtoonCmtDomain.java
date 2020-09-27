package com.allWebtoon.vo;

public class WebtoonCmtDomain extends WebtoonCmtVO {
	private String u_profile;
	private String u_name;
	private String w_thumbnail;
	private String w_title;
	private String w_writer;
	private int w_seq;
	
	public int getW_seq() {
		return w_seq;
	}
	public void setW_seq(int w_seq) {
		this.w_seq = w_seq;
	}
	public String getW_writer() {
		return w_writer;
	}
	public void setW_writer(String w_writer) {
		this.w_writer = w_writer;
	}
	public String getW_thumbnail() {
		return w_thumbnail;
	}
	public void setW_thumbnail(String w_thumbnail) {
		this.w_thumbnail = w_thumbnail;
	}
	public String getW_title() {
		return w_title;
	}
	public void setW_title(String w_title) {
		this.w_title = w_title;
	}
	public String getU_profile() {
		return u_profile;
	}
	public void setU_profile(String u_profile) {
		this.u_profile = u_profile;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
}
