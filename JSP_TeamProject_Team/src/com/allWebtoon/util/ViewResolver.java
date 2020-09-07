package com.allWebtoon.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewResolver {
	public static void viewForward(String fileNm, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
			String jsp = String.format("/WEB-INF/view/%s.jsp", fileNm);
			request.getRequestDispatcher(jsp).forward(request, response);
		}
	public static void accessForward(String fileNm, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		String jsp = String.format("/WEB-INF/access/%s.jsp", fileNm);
		request.getRequestDispatcher(jsp).forward(request, response);
	}
}