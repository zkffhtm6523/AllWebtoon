package com.allWebtoon.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;
import com.allWebtoon.vo.WebtoonVO;
import com.google.gson.Gson;

@WebServlet("/home")
public class HomeSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//홈에 이미지 출력할 배열 생성
		//세션 정보 담긴 변수
		UserVO loginUser = MyUtils.getLoginUser(request);
	 	if(loginUser != null) {
	 		System.out.println("home get profile : "+loginUser.getU_profile());
	 	}
		
		ArrayList<WebtoonVO> list = new ArrayList<WebtoonVO>();
		ArrayList<WebtoonVO> gList = new ArrayList<WebtoonVO>();
		ArrayList<String> genreList = new ArrayList<String>();
		//System.out.println("로그인 유저 없음");
		
		//네이버 정보 출력
		list = WebtoonListDAO.selRandomWebtoonList(list, 1, 15,"");
		//다음 정보 출력
		list = WebtoonListDAO.selRandomWebtoonList(list, 2, 15,"");
		//카카오 정보 출력
		list = WebtoonListDAO.selRandomWebtoonList(list, 3, 15,"");
		//레진 정보 출력
		list = WebtoonListDAO.selRandomWebtoonList(list, 4, 15,"");
		//탑툰 정보 출력
		list = WebtoonListDAO.selRandomWebtoonList(list, 5, 15,"");
	//	list = WebtoonListDAO.selRandomWebtoonList(list,15);
		gList = WebtoonListDAO.selRandomWebtoonList(gList,0, 15,"");
		genreList = WebtoonListDAO.selGenre();
		
		for(String g : genreList) {
			System.out.println(g);
		}
		
		request.setAttribute("list", list);
		request.setAttribute("gList", gList);
		request.setAttribute("genreList", genreList);
		ViewResolver.viewForward("home", request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.getParameter로 값이 안받아져서 다음 방법으로 대체    
		String reqStr = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		reqStr = stringBuilder.toString();


		String genre = reqStr.split("\"")[3];
		
		System.out.println(genre);
		
		ArrayList<WebtoonVO> list = new ArrayList<WebtoonVO>();
		
		list = WebtoonListDAO.selRandomWebtoonList(list,0, 15, genre);
		
		Gson gson = new Gson();
		
		String json = gson.toJson(list);
		
		System.out.println("json : " + json);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(json);
	}
}
