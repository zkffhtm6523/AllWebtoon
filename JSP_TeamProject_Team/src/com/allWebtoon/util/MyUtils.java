package com.allWebtoon.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.allWebtoon.vo.UserVO;

public class MyUtils {

	public static int getLoginUserPk(HttpServletRequest request) {
		return getLoginUserPk(request.getSession());
	}

	public static int getLoginUserPk(HttpSession hs) {
		UserVO loginUser = (UserVO)hs.getAttribute(Const.LOGIN_USER);
		return loginUser == null ? 0 : loginUser.getU_no();
	}

	// 세션의 attr 조회 기능 : 로그인 유저 여부 확인용
	public static UserVO getLoginUser(HttpServletRequest request) {
		HttpSession hs = request.getSession();
		return (UserVO) hs.getAttribute(Const.LOGIN_USER);
	}

	public static int parseStrToInt(String str) {
		return parseStrToInt(str, 0);
	}

	public static int parseStrToInt(String str, int num) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return num;
		}
	}

	public static int getIntParameter(HttpServletRequest request, String keyNm) {
		return parseStrToInt(request.getParameter(keyNm));
	}
}
