package com.allWebtoon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.allWebtoon.db.JdbcSelectInterface;
import com.allWebtoon.db.JdbcTemplate;
import com.allWebtoon.vo.WebtoonCmtDomain;
import com.allWebtoon.vo.WebtoonVO;

public class MyPageDAO {

	public static int myWebtoon(List<WebtoonCmtDomain> list, int u_no) {
		String sql = 
				  " SELECT A.w_thumbnail,"
			//	+ " CASE WHEN char_length(A.w_title) > 8 THEN concat(left(A.w_title, 8), '...') ELSE A.w_title END as w_title,"
				+ " A.w_title, "
				+ " format(B.c_rating,1) as c_rating, A.w_no, B.c_com " 
				+ " FROM t_webtoon A " 
				+ " INNER JOIN t_comment B " 
				+ " ON A.w_no = B.w_no " 
				+ " INNER JOIN t_user C " 
				+ " ON B.u_no = C.u_no " 
				+ " WHERE B.u_no = ? "
				+ " ORDER BY B.m_dt desc";
		
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, u_no);
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					WebtoonCmtDomain vo = new WebtoonCmtDomain();
					vo.setW_thumbnail(rs.getString("w_thumbnail"));
					vo.setW_title(rs.getString("w_title"));
					vo.setC_rating(rs.getFloat("c_rating"));
					vo.setW_no(rs.getInt("w_no"));
					vo.setC_com(rs.getString("c_com"));
					
					
					list.add(vo);
				}
				return 1;
			}
		});
	}
	
	public static int selRecentlyWebtoon(List<WebtoonCmtDomain> list, int u_no) {
		String sql = 
				" select A.w_no, A.w_title, A.w_thumbnail, B.c_com, format(B.c_rating,1) as c_rating, C.w_no, C.u_no " 
				+" from t_webtoon A "
				+" left join t_comment B "
				+" on A.w_no = B.w_no and B.u_no = ? "
				+" inner join t_selwebtoon C "
				+" on A.w_no = C.w_no "
				+" where C.u_no = ? "
				+" order by C.r_dt desc limit 5 ";
	
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, u_no);
				ps.setInt(2, u_no);
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					WebtoonCmtDomain vo = new WebtoonCmtDomain();
					vo.setW_thumbnail(rs.getString("w_thumbnail"));
					vo.setW_title(rs.getString("w_title"));
					vo.setC_rating(rs.getFloat("c_rating"));
					vo.setW_no(rs.getInt("w_no"));
					vo.setC_com(rs.getString("c_com"));
					
					list.add(vo);
				}
				return 1;
			}
		});
	}
	
	public static int selfavoriteWebtoon(List<WebtoonCmtDomain> list, int u_no) {
		String sql = 
				" select B.w_no, B.w_title, B.w_thumbnail, C.c_com, format(C.c_rating,1) as c_rating from t_webtoon_favorite A "
				+" inner join t_webtoon B "
				+" on A.w_no = B.w_no "
				+" left join t_comment C "
				+" on B.w_no = C.w_no and C.u_no = ? "
				+" where A.u_no=? "
				+" order by A.r_dt desc ";
		
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, u_no);
				ps.setInt(2, u_no);
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					WebtoonCmtDomain vo = new WebtoonCmtDomain();
					vo.setW_no(rs.getInt("w_no"));
					vo.setW_title(rs.getString("w_title"));
					vo.setW_thumbnail(rs.getString("w_thumbnail"));
					vo.setC_com(rs.getString("c_com"));
					vo.setC_rating(rs.getFloat("c_rating"));
					
					list.add(vo);
				}
				return 1;
			}
		});
	}
	
	
}
