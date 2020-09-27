package com.allWebtoon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.allWebtoon.db.JdbcSelectInterface;
import com.allWebtoon.db.JdbcTemplate;
import com.allWebtoon.vo.WebtoonCmtDomain;

public class MyPageDAO {

	public static int myWebtoon(List<WebtoonCmtDomain> list, int u_no) {
		String sql = 
				  " SELECT A.w_thumbnail,"
				+ " CASE WHEN char_length(A.w_title) > 8 THEN concat(left(A.w_title, 8), '...') ELSE A.w_title END as w_title,"
				+ " B.c_rating, A.w_no, B.c_com " 
				+ " FROM t_webtoon A " 
				+ " INNER JOIN t_comment B " 
				+ " ON A.w_no = B.w_no " 
				+ " INNER JOIN t_user C " 
				+ " ON B.u_no = c.u_no " 
				+ " WHERE B.u_no = ? "
				+ " ORDER BY B.r_dt ";
		
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
				" SELECT A.w_no, A.u_no, A.r_dt, B.w_title, B.w_thumbnail, C.c_com, C.c_rating "
			+	" FROM t_selwebtoon A "
			+	" INNER JOIN t_webtoon B "
			+	" ON A.w_no = B.w_no "
			+	" left JOIN t_comment C "
			+	" ON B.w_no = C.w_no "
			+	" WHERE A.u_no = ? "
			+	" ORDER BY A.r_dt desc ";
		
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
}
