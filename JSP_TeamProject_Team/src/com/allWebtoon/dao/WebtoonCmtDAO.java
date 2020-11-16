package com.allWebtoon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.allWebtoon.db.JdbcSelectInterface;
import com.allWebtoon.db.JdbcTemplate;
import com.allWebtoon.db.JdbcUpdateInterface;
import com.allWebtoon.vo.WebtoonCmtDomain;
import com.allWebtoon.vo.WebtoonCmtVO;

public class WebtoonCmtDAO {
	//footer에서 평점 갯수 가져오기
	public static int selCntRating() {
		String sql = " SELECT COUNT(*) AS CNT FROM t_comment ";
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				int cnt = 0;
				if(rs.next()) {
					cnt = rs.getInt("CNT");
				}
				return cnt;
			}
		});
	}
	
	//detail페이지에서 댓글, 평점 수정
	public static int updCmt(WebtoonCmtVO param) {
		String sql = " UPDATE t_comment " 
				+ " SET c_com = ? , c_rating = ?, m_dt=now() " 
				+ " WHERE u_no = ? AND w_no = ? ";

		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1, param.getC_com());
				ps.setFloat(2, param.getC_rating());
				ps.setInt(3, param.getU_no());
				ps.setInt(4, param.getW_no());
			}
		});
	}

	public static int insCmt(WebtoonCmtVO param) {
		String sql = " INSERT INTO t_comment " 
				+ " (u_no, w_no, c_com, c_rating) " 
				+ " VALUES "
				+ "(?, ?, ?, ?) ";

		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getU_no());
				ps.setInt(2, param.getW_no());
				ps.setString(3, param.getC_com());
				ps.setFloat(4, param.getC_rating());
			}
		});
	}

	public static WebtoonCmtVO selCmt(WebtoonCmtVO param) {
		WebtoonCmtVO vo = new WebtoonCmtVO();

		String sql = " SELECT c_com, c_rating " 
				+ " FROM t_comment " 
				+ " WHERE u_no = ? and w_no = ? ";

		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getU_no());
				ps.setInt(2, param.getW_no());
			}

			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				if (rs.next()) {
					vo.setC_com(rs.getNString("c_com"));
					vo.setC_rating(rs.getFloat("c_rating"));
				}
				return 1;
			}
		});
		return vo;
	}

	public static List<WebtoonCmtDomain> selCmtList(int w_no) {
		List<WebtoonCmtDomain> list = new ArrayList<WebtoonCmtDomain>();

		String sql = " SELECT B.w_no, A.u_no, A.u_name, A.u_profile, B.c_com, B.c_rating " 
				+ " FROM t_user A " 
				+ " INNER JOIN t_comment B "
				+ " ON A.u_no = B.u_no" 
				+ " WHERE B.w_no = ? "
				+ " ORDER BY B.r_dt " ;

		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, w_no);
			}

			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				while (rs.next()) {
					//if(rs.getString("c_com") != null && !"".equals(rs.getNString("c_com"))) {
						WebtoonCmtDomain vo = new WebtoonCmtDomain();
						vo.setU_name(rs.getNString("u_name"));
						vo.setU_profile(rs.getString("u_profile").equals("") ? null : rs.getString("u_profile"));
						vo.setC_com(rs.getString("c_com"));
						vo.setC_rating(rs.getFloat("c_rating"));
						vo.setU_no(rs.getInt("u_no"));
						vo.setW_no(rs.getInt("w_no"));
						list.add(vo);
					//}
				}
				return 1;
			}
		});
		return list;
	}
	
	public static int delCmt(WebtoonCmtVO param) {
		String sql = " delete from t_comment where w_no=? and u_no=? ";

		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getW_no());
				ps.setInt(2, param.getU_no());
			}
		});
	}
}