package com.allWebtoon.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.SearchWebtoonVO;
import com.google.gson.Gson;

@WebServlet("/searchResult")
public class SearchResultSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchKeyword = request.getParameter("result");
		String writer = request.getParameter("writer");
		String genre = request.getParameter("genre");
		int index = MyUtils.getIntParameter(request, "page");
		
		final int count = 20;
		SearchWebtoonVO vo = new SearchWebtoonVO();
		
		String filterKeyword = scriptFilter(searchKeyword);
		
		vo.setSearchKeyword(filterKeyword);
		
		ArrayList<SearchWebtoonVO> list = new ArrayList<SearchWebtoonVO>();
		ArrayList<SearchWebtoonVO> resultarr = new ArrayList<SearchWebtoonVO>();
		

		if(writer != null && !("".equals(writer))) {
			list = WebtoonListDAO.selSearchList(vo,"writer");
			System.out.println("writer is " + writer);
		} else if(genre != null && !("".equals(genre))) {
			list = WebtoonListDAO.selSearchList(vo,"genre");
		} else {
			
			//한글 사이에 % 넣기. 띄어쓰기에 % 넣기, 영어는 띄어쓰기에만 넣음. 
			String[] str = new String[vo.getSearchKeyword().length()];
			String result = "";
			for(int i=0; i<vo.getSearchKeyword().length(); i++) {
				str[i] = vo.getSearchKeyword().substring(i, i+1);
				if(Pattern.matches("^[ㄱ-ㅎ가-힣]*$", str[i])){
					str[i] = "%" + str[i] + "%";
				} else if(" ".equals(str[i])) {
					str[i] = "%";
				}
			}
			
			for(int i=0; i<str.length; i++) {
				result += str[i];
			}
			vo.setSearchKeyword(result);
			list =  WebtoonListDAO.selSearchList(vo,"all");
		}
		
		//ajax 통신으로 받아온 index값이 존재하지 않으면(초기화면)
		if(index == 0) {
			for(int i=0; i<list.size() && i<count; i++) {
				resultarr.add(list.get(i));
			}
			request.setAttribute("result", resultarr);
			request.setAttribute("keyword", searchKeyword);
			request.setAttribute("count", count);
			
			ViewResolver.viewForward("searchResult", request, response);
		} else {
			for(int i=index; i<(index+count) && i<list.size(); i++) {
				resultarr.add(list.get(i));
			}
			
			Gson gson = new Gson();
			String json = gson.toJson(resultarr);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(json);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	//스크립트 필터
 	private String scriptFilter(final String ctnt) {
 		String[] filters = {"<script>", "</script>","\"\">"};
 		String[] filterReplaces = {"&lt;script&gt;", "&lt;/script&gt;","\"\""};
 		
 		String result = ctnt;
 		for(int i=0; i<filters.length; i++) {
 			result = result.replace(filters[i], filterReplaces[i]);
 		}
 		return result;
 	}

}
