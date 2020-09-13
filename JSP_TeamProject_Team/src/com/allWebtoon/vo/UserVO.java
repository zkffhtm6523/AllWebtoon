package com.allWebtoon.vo;

public class UserVO {
	private int u_no;
	private String u_id;
	private String u_password;
	private String u_name;
	private String u_birth;
	private String gender_name;
	private String u_email;
	private String u_profile;
	private String[] u_genre;
	private String r_dt;
	private String m_dt;
	private int u_joinPath;
	private String chkProfile;
	
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getU_password() {
		return u_password;
	}
	public void setU_password(String u_password) {
		this.u_password = u_password;
	}
	public int getU_no() {
		return u_no;
	}
	public void setU_no(int u_no) {
		this.u_no = u_no;
	}
	
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public String getU_birth() {
		return u_birth;
	}
	public void setU_birth(String u_birth) {
		this.u_birth = u_birth;
	}
	public String getGender_name() {
		return gender_name;
	}
	public void setGender_name(String gender_name) {
		this.gender_name = gender_name;
	}
	public String getU_email() {
		return u_email;
	}
	public void setU_email(String u_email) {
		this.u_email = u_email;
	}
	public String getU_profile() {
		return u_profile;
	}
	public void setU_profile(String u_profile) {
		this.u_profile = u_profile;
	}
	public String[] getU_genre() {
		return u_genre;
	}
	public void setU_genre(String[] u_genre) {
		this.u_genre = u_genre;
	}
	public String getR_dt() {
		return r_dt;
	}
	public void setR_dt(String r_dt) {
		this.r_dt = r_dt;
	}
	public String getM_dt() {
		return m_dt;
	}
	public void setM_dt(String m_dt) {
		this.m_dt = m_dt;
	}
	public int getU_joinPath() {
		return u_joinPath;
	}
	public void setU_joinPath(int u_joinPath) {
		this.u_joinPath = u_joinPath;
	}
	public String getChkProfile() {
		return chkProfile;
	}
	public void setChkProfile(String chkProfile) {
		this.chkProfile = chkProfile;
	}
	
}
