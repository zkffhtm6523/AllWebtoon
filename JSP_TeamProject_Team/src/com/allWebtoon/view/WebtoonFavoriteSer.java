package com.allWebtoon.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allWebtoon.dao.UserDAO;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.vo.WebtoonCmtVO;
import com.google.gson.Gson;

@WebServlet("/webtoon/favorite")
public class WebtoonFavoriteSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// keep(보고파요) ajax를 위한 servlet
		int w_no = MyUtils.getIntParameter(request, "w_no");

		WebtoonCmtVO param = new WebtoonCmtVO();
		int u_no = MyUtils.getLoginUser(request).getU_no();

		param.setU_no(u_no);
		param.setW_no(w_no);
		int result = 0;
		String proc_type = request.getParameter("proc_type");
		switch (proc_type) {
		case "ins":
			result = UserDAO.insFavorite(param);
			break;
		case "del":
			result = UserDAO.delFavorite(param);
			break;
		}
		Gson gson = new Gson();
		String json = gson.toJson(result);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(json);

	}
}
