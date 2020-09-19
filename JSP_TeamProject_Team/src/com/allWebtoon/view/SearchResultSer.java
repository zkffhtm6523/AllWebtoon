package com.allWebtoon.view;

import java.io.IOException;
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
import com.allWebtoon.vo.SearchWebtoonVO;
import com.google.gson.Gson;

@WebServlet("/searchResult")
public class SearchResultSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchKeyword = request.getParameter("result");
		int index = MyUtils.getIntParameter(request, "page");
		
		final int count = 20;
		SearchWebtoonVO vo = new SearchWebtoonVO();
		vo.setSearchKeyword(searchKeyword);
		
		ArrayList<SearchWebtoonVO> list =  WebtoonListDAO.selSearchList(vo);
		ArrayList<SearchWebtoonVO> resultarr = new ArrayList<SearchWebtoonVO>();
		
		
		if(index == 0) {
			for(int i=0; i<count; i++) {
				if(i==list.size()) {
					break;
				}
				resultarr.add(list.get(i));
			}
			request.setAttribute("result", resultarr);
			request.setAttribute("count", count);
			request.setAttribute("keyword", searchKeyword);
			//request.setAttribute("data", vo);
			
			ViewResolver.viewForward("searchResult", request, response);
		} else {
			
			for(int i=index; i<(index+count); i++) {
				if(i>=list.size()) {
					break;
				}
				resultarr.add(list.get(i));
				
			}
			
			Gson gson = new Gson();
			
			String json = gson.toJson(resultarr);
			
			System.out.println("json : " + json);
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(json);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
