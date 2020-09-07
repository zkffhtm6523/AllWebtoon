package com.allWebtoon.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.SearchWebtoonVO;

@WebServlet("/searchResult")
public class SearchResultSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchKeyword = request.getParameter("result");
		
		SearchWebtoonVO vo = new SearchWebtoonVO();
		vo.setSearchKeyword(searchKeyword);
		
		ArrayList<SearchWebtoonVO> list =  WebtoonListDAO.selSearchList(vo, 10);
		
		request.setAttribute("result", list);
		request.setAttribute("data", vo);
		
		ViewResolver.viewForward("searchResult", request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
