package com.allWebtoon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.allWebtoon.db.JdbcSelectInterface;
import com.allWebtoon.db.JdbcTemplate;
import com.allWebtoon.db.JdbcUpdateInterface;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.SecurityUtils;
import com.allWebtoon.vo.SearchWebtoonVO;
import com.allWebtoon.vo.UserVO;
import com.allWebtoon.vo.WebtoonCmtDomain;
import com.allWebtoon.vo.WebtoonCmtVO;
import com.allWebtoon.vo.WebtoonVO;

public class WebtoonListDAO {
	//홈화면 출력  
	//platformNum =0 , randomLength=0 이면 전체  
	public static ArrayList<WebtoonVO> selRandomWebtoonList(ArrayList<WebtoonVO> list, int platformNum, int randomLength, String genre){
		String sql = "select w_no, w_title, w_story, w_writer, w_thumbnail, w_link, plat_no from view_webtoon ";

		if(platformNum != 0) {
			sql += " where plat_no =? ";
			if(!genre.equals("")) {
				sql+= " AND genre_name like ? ";
			}
		} else {
			if(!genre.equals("") && !genre.equals("전체") ) {
				sql += " where genre_name like ? ";
			}
		}
		sql += " order by rand() ";
		
		if(randomLength != 0) {
			sql += " limit ? ";
		}

		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			@Override
			//물음표 넣을 때
			public void prepared(PreparedStatement ps) throws SQLException {
				int idx = 1;
				if(platformNum != 0) {
					ps.setInt(idx++, platformNum);
				}
				if(!genre.equals("") && !genre.equals("전체")) {
					ps.setNString(idx++, "%"+genre+"%");
				}
				if(randomLength != 0) {
					ps.setInt(idx++, randomLength);
				}
				
			/*	if(platformNum != 0) {
					ps.setInt(1, platformNum);
					if(randomLength != 0) {
						ps.setInt(2, randomLength);
					}
				}else {
					if(randomLength != 0) {
						ps.setInt(1, randomLength);
					}
				}*/
			}
			@Override
			//while문으로 값 가져올 때
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					WebtoonVO vo = new WebtoonVO();
					vo.setW_no(rs.getInt("w_no"));
					vo.setW_title(rs.getNString("w_title"));
					vo.setW_story(rs.getNString("w_story"));
					vo.setW_writer(rs.getNString("w_writer"));
					vo.setW_thumbnail(rs.getNString("w_thumbnail"));
					vo.setW_link(rs.getNString("w_link"));
					vo.setW_plat_no(rs.getInt("plat_no"));
					//vo.setU_no(rs.getInt("u_no"));
					//vo.setC_rating(rs.getFloat("c_rating"));
					list.add(vo);
				}
				return 1;
			}
		});
		
		return list;
	}
	
	
	public static ArrayList<WebtoonCmtVO> selCmtList(ArrayList<WebtoonCmtVO> list, int u_no){
		String sql = " select w_no, u_no, c_rating from t_comment where u_no=? ";

		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			@Override
			//물음표 넣을 때
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, u_no);
			}
			@Override
			//while문으로 값 가져올 때
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					WebtoonCmtVO vo = new WebtoonCmtVO();
					vo.setW_no(rs.getInt("w_no"));
					vo.setU_no(rs.getInt("u_no"));
					vo.setC_rating(rs.getFloat("c_rating"));
					list.add(vo);
				}
				return 1;
			}
		});
		
		return list;
	}
	
	// 검색 결과
	public static ArrayList<SearchWebtoonVO> selSearchList(SearchWebtoonVO vo, String kind){
		ArrayList<SearchWebtoonVO> list = new ArrayList<SearchWebtoonVO>();
		String sql = 
			/*" SELECT w_no, w_title, CASE WHEN char_length(w_story) > 150 THEN concat(left(w_story, 150), '...') ELSE w_story END as w_story, w_thumbnail, w_link, plat_no, " + 
			"		  	genre_name, group_concat(w_writer separator ', ') as w_writer, " + 
			"		    plat_name  from view_webtoon "
		  + " where w_title LIKE ? or genre_name LIKE ? or w_writer LIKE ? or plat_name LIKE ? "
		  + " group by w_no ";*/
				
			" select a.w_no, a.w_title,CASE WHEN char_length(a.w_story) > 150 THEN concat(left(a.w_story, 150), '...') ELSE a.w_story END as w_story, "
			+ "	a.w_thumbnail, a.w_link, a.plat_no, a.genre_name, group_concat(a.w_writer separator ', ') as w_writer, "
			+ "	a.plat_name from view_webtoon A ";

			if(kind.equals("writer")) {
				sql += " inner join t_w_writer B "
				+ " on a.w_no = b.w_no "
				+ " where b.w_writer = ? ";
			} else if(kind.equals("genre")) {
				sql += " where genre_name like ? ";
			}
			else if(kind.equals("all")){
				sql += " where w_title LIKE ? or w_writer LIKE ? or plat_name LIKE ? ";
			}
			
		sql += " group by a.w_no ";

		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				if(kind.equals("all")) {
					ps.setNString(1, "%"+vo.getSearchKeyword()+"%");
					ps.setNString(2, "%"+vo.getSearchKeyword()+"%");
					ps.setNString(3, "%"+vo.getSearchKeyword()+"%");
				} else if(kind.equals("genre")) {
					ps.setNString(1, "%"+vo.getSearchKeyword()+"%");
				} else if(kind.equals("writer")) {
					ps.setNString(1, vo.getSearchKeyword());
				}
				
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
		public static WebtoonVO webtoonDetail(int w_no, int u_no) {
			WebtoonVO vo = new WebtoonVO();
			String sql = 
					" SELECT A.w_thumbnail, A.w_title,  CASE WHEN char_length(A.w_story) > 300 THEN concat(left(A.w_story, 300), '...') ELSE A.w_story END as w_story, " + 
					" A.w_link, B.plat_name, group_concat(C.w_writer separator ', ') as w_writer, E.genre_name, E.genre_name, CASE WHEN F.w_no IS NULL then 0 ELSE 1 END AS is_favorite " + 
					" FROM t_webtoon A " + 
					" LEFT JOIN t_platform B " + 
					" ON A.plat_no = B.plat_no " + 
					" LEFT JOIN t_w_writer C " + 
					" ON A.w_no = C.w_no " + 
					" LEFT JOIN t_w_genre D " + 
					" ON A.w_no = D.w_no " + 
					" LEFT JOIN t_genre E " + 
					" ON D.genre_no = E.genre_no " +
					" LEFT JOIN t_webtoon_favorite F " + 
					" ON A.w_no = F.w_no " + 
					" AND F.u_no = ? " + 
					" WHERE A.w_no = ? ; ";

			JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

				@Override
				public void prepared(PreparedStatement ps) throws SQLException {
					ps.setInt(1, u_no);
					ps.setInt(2, w_no);
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
						vo.setIs_favorite(rs.getInt("is_favorite"));
					}
					return 1;
				}
			});
			return vo;
		}
			
		public static ArrayList<String> selGenre(){
			ArrayList<String> genreList = new ArrayList<String>();
			
			String sql = " select genre_no, genre_name from t_genre ";
			
			JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

				@Override
				public void prepared(PreparedStatement ps) throws SQLException {
					
				}

				@Override
				public int executeQuery(ResultSet rs) throws SQLException {
					while(rs.next()) {
						genreList.add(rs.getNString("genre_name"));
					}
					return 1;
				}
			});
			return genreList;
			
		}
		
		
		public static int insSelWebtoon(int w_no, int u_no) {
			String sql = "INSERT INTO t_selwebtoon "
					+ " (w_no, u_no) "
					+ " VALUES (?,?) ";
			
			return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
				@Override
				public void update(PreparedStatement ps) throws SQLException {
					ps.setInt(1, w_no);
					ps.setInt(2, u_no);
				}

			});
		}
		
		public static int updSelWebtoon(int w_no, int u_no) {
			String sql = "UPDATE t_selwebtoon "
					+ " SET r_dt=now() "
					+ " WHERE w_no= ? and u_no=? ";
			
			
			return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
				@Override
				public void update(PreparedStatement ps) throws SQLException {
					ps.setInt(1, w_no);
					ps.setInt(2, u_no);
				}
			});
		}
		
		public static int delselWebtoon(int u_no) {
			
			String sql = " delete from t_selwebtoon " + 
					"where u_no= ? and w_no not in (select new.ww_no from (select w_no as ww_no from t_selwebtoon where u_no=? order by r_dt desc limit 5) new) ";
			
			
			return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
				@Override
				public void update(PreparedStatement ps) throws SQLException {
					ps.setInt(1, u_no);
					ps.setInt(2, u_no);
				}
			});
		}
}
