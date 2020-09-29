package com.allWebtoon.vo;

public class WebtoonCmtVO {
	private int w_no;
	private int u_no;
	private String c_com;
	private float c_rating;
	private String genre_name;
	private String proc_type;

	public String getProc_type() {
		return proc_type;
	}

	public void setProc_type(String proc_type) {
		this.proc_type = proc_type;
	}

	public String getGenre_name() {
		return genre_name;
	}

	public void setGenre_name(String genre_name) {
		this.genre_name = genre_name;
	}

	public int getW_no() {
		return w_no;
	}

	public void setW_no(int w_no) {
		this.w_no = w_no;
	}

	public int getU_no() {
		return u_no;
	}

	public void setU_no(int u_no) {
		this.u_no = u_no;
	}

	public String getC_com() {
		return c_com;
	}

	public void setC_com(String c_com) {
		this.c_com = c_com;
	}

	public float getC_rating() {
		return c_rating;
	}

	public void setC_rating(float c_rating) {
		this.c_rating = c_rating;
	}

}