package com.allWebtoon.crawling;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.allWebtoon.db.JdbcTemplate;
import com.allWebtoon.db.JdbcUpdateInterface;

public class CrawWebtoonDAO {
	public static void insWebtoonList(CrawWebtoonVO param) {
		String w_sql = " insert into t_webtoon "
				+ " (plat_no, w_title, w_story, w_thumbnail, w_link) "
				+ " values "
				+ " (?, ?, ?, ?, ?) ";
		JdbcTemplate.executeUpdate(w_sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getPlatform());
				ps.setNString(2, param.getTitle());
				ps.setNString(3, param.getStory());
				ps.setNString(4, param.getThumbnail());
				ps.setNString(5, param.getLink());
				System.out.println("값 넣기 성공");
			}
		});
		
		for(String writer : param.getWriter()) {
			inst_writer(param, writer);
		}
		
		for(int genre : param.getGenre()) {
			inst_genre(param, genre);
		}
	}
	
	public static void inst_writer(CrawWebtoonVO param, String writer) {
		String sql = "insert into t_w_writer(w_no, w_writer) values ((select w_no from t_webtoon where w_title=?),?)";

		JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
		
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getTitle());
				ps.setNString(2, writer);
				
			}
		});
	}
	
	public static void inst_genre(CrawWebtoonVO param, int genre) {
		String sql = "insert into t_w_genre(w_no, genre_no) values ((select w_no from t_webtoon where w_title=?),?)";

		JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
		
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getTitle());
				ps.setInt(2, genre);
				
			}
		});
	}
	
}
