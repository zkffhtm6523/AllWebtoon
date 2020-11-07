package com.allWebtoon.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/SNSController")
public class SNSController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String snsChk = request.getParameter("snsPlatform");
		switch (snsChk) {
		case "kakao":
			response.sendRedirect(SNSInfo.kakao_login());
			break;
		case "google":
			response.sendRedirect(SNSInfo.google_login());
			break;
		case "naver":
			response.sendRedirect(SNSInfo.naver_login());
			break;
		default:
			response.sendRedirect("/login");
			break;
		}
	}
}
