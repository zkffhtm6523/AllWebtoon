package com.allWebtoon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.allWebtoon.db.JdbcSelectInterface;
import com.allWebtoon.db.JdbcTemplate;
import com.allWebtoon.vo.SearchWebtoonVO;
import com.allWebtoon.vo.WebtoonVO;

public class WebtoonListDAO {
	// 홈화면 출력 
	public static ArrayList<WebtoonVO> selRandomWebtoonList(ArrayList<WebtoonVO> list, int platformNum, int randomLength){
		String sql = " select w_no, w_title, w_story, w_thumbnail, w_link, plat_no "
					+ " from t_webtoon "
					+ " where plat_no = ? "
					+ " order by rand() limit ? ";
		
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			@Override
			//물음표 넣을 때
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, platformNum);
				ps.setInt(2, randomLength);
			}
			@Override
			//while문으로 값 가져올 때
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					WebtoonVO vo = new WebtoonVO();
					vo.setW_no(rs.getInt("w_no"));
					vo.setW_title(rs.getNString("w_title"));
					vo.setW_story(rs.getNString("w_story"));
					vo.setW_thumbnail(rs.getNString("w_thumbnail"));
					vo.setW_plat_no(rs.getInt("plat_no"));
					list.add(vo);
				}
				return 1;
			}
		});
		
		return list;
	}
	
	public static ArrayList<WebtoonVO> selRandomWebtoonList(ArrayList<WebtoonVO> list){
		String sql = " select w_no, w_title, w_writer, w_story, w_thumbnail, w_link, plat_no "
					+ " from view_webtoon "
					+ " order by rand()";

		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			@Override
			//물음표 넣을 때
			public void prepared(PreparedStatement ps) throws SQLException {
				//ps.setInt(1, randomLength);
			}
			@Override
			//while문으로 값 가져올 때
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					WebtoonVO vo = new WebtoonVO();
					vo.setW_no(rs.getInt("w_no"));
					vo.setW_title(rs.getNString("w_title"));
					vo.setW_writer(rs.getNString("w_writer"));
					vo.setW_story(rs.getNString("w_story"));
					vo.setW_thumbnail(rs.getNString("w_thumbnail"));
					vo.setW_plat_no(rs.getInt("plat_no"));
					list.add(vo);
				}
				return 1;
			}
		});
		
		return list;
	}
	
	
	// 검색 결과
	public static ArrayList<SearchWebtoonVO> selSearchList(SearchWebtoonVO vo){
		ArrayList<SearchWebtoonVO> list = new ArrayList<SearchWebtoonVO>();
		String sql = 
			" SELECT w_no, w_title, concat(LEFT(w_story, 150), '...') as w_story, w_thumbnail, w_link, plat_no, " + 
			"		  	genre_name, group_concat(w_writer separator ', ') as w_writer, " + 
			"		    plat_name  from view_webtoon "
		  + " where w_title LIKE ? or genre_name LIKE ? or w_writer LIKE ? or plat_name LIKE ? "
		  + " group by w_no ";

		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setNString(1, "%"+vo.getSearchKeyword()+"%");
				ps.setNString(2, "%"+vo.getSearchKeyword()+"%");
				ps.setNString(3, "%"+vo.getSearchKeyword()+"%");
				ps.setNString(4, "%"+vo.getSearchKeyword()+"%");
				
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					SearchWebtoonVO param = new SearchWebtoonVO();
					param.setW_no(rs.getInt("w_no"));
					param.setW_title(rs.getNString("w_title"));
					param.setW_story(rs.getNString("w_story"));
					param.setW_thumbnail(rs.getNString("w_thumbnail"));
					param.setGenre_name(rs.getNString("genre_name"));
					param.setW_writer(rs.getNString("w_writer"));
					param.setW_link(rs.getNString("w_link"));
					list.add(param);
				}
				return 1;
			}
		});
		return list;
	}
	// 웹툰 디테일
		public static WebtoonVO webtoonDetail(int w_no) {
			WebtoonVO vo = new WebtoonVO();
			String sql = 
					" select w_thumbnail, w_title, concat(left(w_story, 300),'…') as w_story, "
					+ " w_link, plat_name, group_concat(w_writer separator ', ') as w_writer, genre_name "
					+ " from view_webtoon "
					+ " WHERE w_no = ? ";

			JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

				@Override
				public void prepared(PreparedStatement ps) throws SQLException {
					ps.setInt(1, w_no);
				}

				@Override
				public int executeQuery(ResultSet rs) throws SQLException {
					if(rs.next()) {
						vo.setW_thumbnail(rs.getNString("w_thumbnail"));
						vo.setW_title(rs.getNString("w_title"));
						vo.setW_story(rs.getNString("w_story"));
						vo.setW_link(rs.getNString("w_link"));
						vo.setW_plat_name(rs.getNString("plat_name"));
						vo.setW_writer(rs.getNString("w_writer"));
						vo.setGenre_name(rs.getNString("genre_name"));
					}
					return 1;
				}
			});
			return vo;
		}
	
}