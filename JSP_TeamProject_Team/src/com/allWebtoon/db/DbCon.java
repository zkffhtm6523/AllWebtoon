package com.allWebtoon.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbCon {
	public static Connection getCon() throws Exception{
		//mysql의 포트번호/데이터베이스명을 적는다.
		String dbURL = "jdbc:mysql://localhost:3306/webtoon_db?serverTimezone=UTC";
		String dbID = "root";
		String dbPassword = "root";		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
		return conn;
	}
	
	public static void closeCon(Connection con, PreparedStatement ps, ResultSet rs) {
		if (rs != null) {try {rs.close();} catch (Exception e) {}}
		if (ps != null) {try {ps.close();} catch (Exception e) {}}
		if (con != null) {try {con.close();} catch (Exception e) {}}
	}
	
	public static void closeCon(Connection con, PreparedStatement ps) {
		closeCon(con, ps, null);
	}
}
