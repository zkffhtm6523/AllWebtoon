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
				
			" select A.w_no, A.w_title,CASE WHEN char_length(A.w_story) > 100 THEN concat(left(A.w_story, 100), '...') ELSE A.w_story END as w_story, "
			+ "	A.w_thumbnail, A.w_link, A.plat_no, A.genre_name, group_concat(A.w_writer separator ', ') as w_writer, "
			+ "	A.plat_name from view_webtoon A ";

			if(kind.equals("writer")) {
				sql += " inner join t_w_writer B "
				+ " on A.w_no = B.w_no "
				+ " where B.w_writer = ? ";
			} else if(kind.equals("genre")) {
				sql += " where genre_name like ? ";
			}
			else if(kind.equals("all")){
				sql += " where w_title LIKE ? or w_writer LIKE ? or plat_name LIKE ? ";
			}
			
		sql += " group by A.w_no ";

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
					" select A.w_no,w_thumbnail, w_title,CASE WHEN char_length(w_story) > 100 THEN concat(left(w_story, 100), '...') ELSE w_story END as w_story, "
					+" w_link, plat_name, group_concat(w_writer separator ', ') as w_writer, genre_name, CASE WHEN B.w_no IS NULL then 0 ELSE 1 END AS is_favorite "
					+" from view_webtoon A "
					+" LEFT JOIN t_webtoon_favorite B " 
					+" ON A.w_no = B.w_no and B.u_no=? "
					+" where A.w_no=? "
					+" group by w_no ";

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
			
			String sql = " delete from t_selwebtoon " 
					+ " where u_no = ? "
					+ " and w_no not in "
					+ " (select new.ww_no"
					+ "		from (select w_no as ww_no from "
					+ "			  t_selwebtoon where u_no = ? "
					+ "			  order by r_dt desc limit 5)"
					+ "		as new) ";
			
			
			return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
				@Override
				public void update(PreparedStatement ps) throws SQLException {
					ps.setInt(1, u_no);
					ps.setInt(2, u_no);
				}
			});
		}
		
		//추천 시 사용
		public static WebtoonVO selrecommendWebtoon(int w_no) {
			String sql = 
					" select w_no, w_title, w_thumbnail from t_webtoon "
					+" where w_no=? ";
			
			WebtoonVO vo = new WebtoonVO();
			
			JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
				
				@Override
				public void prepared(PreparedStatement ps) throws SQLException {
					ps.setInt(1, w_no);
				}
				
				@Override
				public int executeQuery(ResultSet rs) throws SQLException {
					while(rs.next()) {
						vo.setW_no(rs.getInt("w_no"));
						vo.setW_title(rs.getString("w_title"));
						vo.setW_thumbnail(rs.getString("w_thumbnail"));
					}
					return 1;
				}
			});
			
			return vo;
		}
		//데이터 모델 변환용
		public static List<WebtoonCmtDomain> selDataModel(String genre_name){
			List<WebtoonCmtDomain> webtoonList = new ArrayList<WebtoonCmtDomain>();
			String sql = " SELECT A.u_no, A.w_no, A.c_rating FROM t_comment A "
					+ " INNER JOIN t_w_genre B "
					+ " ON A.w_no = B.w_no "
					+ " INNER JOIN t_genre C "
					+ " ON B.genre_no = C.genre_no "
					//+ " WHERE C.genre_name = ? 
					+ " ORDER BY A.u_no ";
			
			JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
				
				@Override
				public void prepared(PreparedStatement ps) throws SQLException {
					//ps.setString(1, genre_name);
				}
				@Override
				public int executeQuery(ResultSet rs) throws SQLException {
					List<WebtoonCmtDomain> tempList = new ArrayList<WebtoonCmtDomain>();
					int idx = 0;
					while (rs.next()) {
						WebtoonCmtDomain tempVo = new WebtoonCmtDomain();
						tempVo.setU_no(rs.getInt("u_no"));
						tempVo.setW_no(rs.getInt("w_no"));
						tempVo.setC_rating(rs.getFloat("c_rating"));
						tempList.add(tempVo);
						//webtoonList.add(tempVo);
						idx++;
					}
					Integer category = null;
					WebtoonCmtDomain tempVo = null;
					
					for (int i = 0; i < tempList.size(); i++) {
						if(category == null || (category != null &&
								!category.equals(tempList.get(i).getU_no()))) {
							//앞의 u_no가 바뀔때만 생성
							category = tempList.get(i).getU_no();
							//완료
							WebtoonCmtDomain vo = new WebtoonCmtDomain();
							vo.setU_no(tempList.get(i).getU_no());
							
							List<WebtoonCmtVO> w_list = new ArrayList<WebtoonCmtVO>();
							vo.setW_list(w_list);
							tempVo = vo;
							webtoonList.add(vo);
							
							WebtoonCmtVO w_param = new WebtoonCmtVO();
							w_param.setW_no(tempList.get(i).getW_no());
							w_param.setC_rating(tempList.get(i).getC_rating());
							w_list.add(w_param);
							tempVo.setW_list(w_list);
							if(i == tempList.size()-1) {
								break;
							}else if(tempList.get(i).getU_no() != tempList.get(i+1).getU_no()){
								continue;
							}else {
								i++;
							}
						}
						WebtoonCmtVO w_param1 = new WebtoonCmtVO();
						w_param1.setW_no(tempList.get(i).getW_no());
						w_param1.setC_rating(tempList.get(i).getC_rating());
						tempVo.getW_list().add(w_param1);
					}
					
					return 1;
				}
			});
			
			return webtoonList;
		}
}
		
