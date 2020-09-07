package com.allWebtoon.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
	//SQL select 제외 사용 메소드
	public static int executeUpdate(String sql, JdbcUpdateInterface jdbc) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			
			//콜백 함수 실행
			jdbc.update(ps);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 에러");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQL 제외 에러");
		} finally {
			DbCon.closeCon(con, ps);
		}
		return result;
	}
	//SQL select 필요 시 사용하는 메소드
	public static int executeQuery(String sql, JdbcSelectInterface jdbc) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DbCon.getCon();
			ps = con.prepareStatement(sql);
			//물음표(?)에 setInt, setNString 할 때 사용하는 메소드
			jdbc.prepared(ps);
			
			rs = ps.executeQuery();
			result = jdbc.executeQuery(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL 에러");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQL 제외 에러");
		}
		return result;
	}
}
