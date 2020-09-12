package com.allWebtoon.vo;

public class UserVO {
	private int u_no;
	private String user_id;
	private String user_password;
	private String name;
	private String birth;
	private String gender;
	private String email;
	private String profile;
	private String[] u_genre;
	private String r_dt;
	private String m_dt;
	private int u_joinPath;
	private String chkProfile;
	
	public String getChkProfile() {
		return chkProfile;
	}
	public void setChkProfile(String chkProfile) {
		this.chkProfile = chkProfile;
	}
	public int getU_joinPath() {
		return u_joinPath;
	}
	public void setU_joinPath(int u_joinPath) {
		this.u_joinPath = u_joinPath;
	}
	public String[] getU_genre() {
		return u_genre;
	}
	public void setU_genre(String[] u_genre) {
		this.u_genre = u_genre;
	}
	public int getU_no() {
		return u_no;
	}
	public void setU_no(int u_no) {
		this.u_no = u_no;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String password) {
		this.user_password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
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
	
	
}
