package com.allWebtoon.api;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;

public class SNSInfo {
	//REDIRECT_URI
	private static final String KAKAO_REDIRECT_URI = "http://allwebtoon.xyz/kakaoAPI";
	//private static final String KAKAO_REDIRECT_URI = "http://localhost:8089/kakaoAPI";
	private static final String GOOGLE_REDIRECT_URI = "http://allwebtoon.xyz/googleAPI";
	private static final String NAVER_REDIRECT_URI = "http://allwebtoon.xyz/naverAPI";
	
	//공통 정보
	private static final String RESPONSE_TYPE = "&response_type=code";
	private static final String REDIRECT_URI = "&redirect_uri=";
	
	//카카오 정보
	private static final String KAKAO_URI = "https://kauth.kakao.com/oauth/authorize?";
	private static final String KAKAO_CLIENT_ID = "client_id=48c16d63af5493c7ae43a1433ec7760f";
	
	//구글 정보
	private static final String GOOGLE_URI = "https://accounts.google.com/o/oauth2/auth?";
	private static final String GOOGLE_CLIENT_ID = "client_id=659641044041-d8d9d26ubldu5veldv2g3cqaqedv6htq.apps.googleusercontent.com";
	private static final String GOOGLE_SCOPE = "&scope=https://www.googleapis.com/auth/userinfo.email"
											+ "+https://www.googleapis.com/auth/plus.me"
											+ "+https://www.googleapis.com/auth/userinfo.profile";
	private static final String GOOGLE_APPROVAL_PROMPT = "&approval_prompt=force";
	private static final String GOOGLE_ACCESS_TYPE = "&access_type=offline";
	private static final String GOOGLE_CLIENT_SECRET = "client_secret=LxGdpTGyFqWFj3AT1167xbvF";
	
	//네이버 정보
	private static final String NAVER_URI = "https://nid.naver.com/oauth2.0/authorize?";
	private static final String NAVER_CLIENT_ID = "client_id=gtb_8Ij5V31vLTCJA7F3";
	private static final String NAVER_CLIENT_SECRET = "client_secret=8dYiJWFqmT";
	
	public static String kakao_login() {
		StringBuilder sb = new StringBuilder();
		sb.append(SNSInfo.KAKAO_URI);
		sb.append(SNSInfo.KAKAO_CLIENT_ID);
		sb.append(SNSInfo.REDIRECT_URI+SNSInfo.KAKAO_REDIRECT_URI);
		sb.append(SNSInfo.RESPONSE_TYPE);
		return sb.toString();
	}
	public static String google_login() {
		StringBuilder sb = new StringBuilder();
		sb.append(SNSInfo.GOOGLE_URI);
		sb.append(SNSInfo.GOOGLE_CLIENT_ID);
		sb.append(SNSInfo.GOOGLE_SCOPE);
		sb.append(SNSInfo.GOOGLE_APPROVAL_PROMPT);
		sb.append(SNSInfo.GOOGLE_ACCESS_TYPE);
		sb.append(SNSInfo.RESPONSE_TYPE);
		sb.append(SNSInfo.REDIRECT_URI+SNSInfo.GOOGLE_REDIRECT_URI);
		return sb.toString();
	}
	public static String naver_login() throws UnsupportedEncodingException {
		String redirectURI = URLEncoder.encode(SNSInfo.NAVER_REDIRECT_URI, "UTF-8");
		SecureRandom random = new SecureRandom();
		String state = "&state="+new BigInteger(130, random).toString();

		StringBuilder sb = new StringBuilder();
		sb.append(SNSInfo.NAVER_URI);
		sb.append(SNSInfo.NAVER_CLIENT_ID);
		sb.append(SNSInfo.RESPONSE_TYPE);
		sb.append(SNSInfo.REDIRECT_URI+redirectURI);
		sb.append(state);
		return sb.toString();
	}
	public static String getKakaoRedirectUri() {
		return KAKAO_REDIRECT_URI;
	}
	public static String getGoogleRedirectUri() {
		return GOOGLE_REDIRECT_URI;
	}
	public static String getNaverRedirectUri() {
		return NAVER_REDIRECT_URI;
	}
	public static String getKakaoClientId() {
		return KAKAO_CLIENT_ID;
	}
	public static String getGoogleClientId() {
		return GOOGLE_CLIENT_ID;
	}
	public static String getNaverClientId() {
		return NAVER_CLIENT_ID;
	}
	public static String getNaverClientSecret() {
		return NAVER_CLIENT_SECRET;
	}
	public static String getGoogleClientSecret() {
		return GOOGLE_CLIENT_SECRET;
	}
	
}
