package com.allWebtoon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.allWebtoon.db.JdbcSelectInterface;
import com.allWebtoon.db.JdbcTemplate;
import com.allWebtoon.db.JdbcUpdateInterface;
import com.allWebtoon.util.SecurityUtils;
import com.allWebtoon.vo.UserVO;
import com.allWebtoon.vo.WebtoonCmtVO;

public class UserDAO {
	public static int insUser(UserVO param) {
		
		String sql = "INSERT INTO t_user"
				+ " (u_id, u_password, u_name, u_birth, gender_no, u_email, u_profile, u_joinPath, u_salt) "
				+ " VALUES (?,?,?,?,?,?,?,?,?) ";
		
		return JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				
				//param.setU_salt(u_salt);
				//param.setU_encrypt();
				ps.setNString(1,param.getU_id());
				ps.setNString(2, param.getU_password());
				ps.setNString(3, param.getU_name());
				ps.setNString(4, param.getU_birth());
				if(param.getGender_name().equals("female") || param.getGender_name().equals("여성")) {
					ps.setInt(5, 1);
				} else {
					ps.setInt(5, 2);
				}
				ps.setNString(6, param.getU_email());
				if(param.getU_profile() == null) {
					ps.setNString(7, "");
				}else {
					ps.setNString(7, param.getU_profile());
				}
				if(param.getU_joinPath() > 1) {
					ps.setInt(8, param.getU_joinPath());
				}else {
					ps.setInt(8, 1);
				}
				ps.setString(9, param.getU_salt());
			}

		});
	}
	public static int selSNSUser(UserVO param) {
		String sql = "SELECT u_no, u_password, u_name, r_dt, m_dt, u_birth, u_salt, u_profile, gender_no FROM t_user WHERE u_id=? ";
		
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setNString(1,  param.getU_id());
			}
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				if(rs.next()) {					//레코드가 있음
					String dbPw = rs.getNString("u_password");
					String salt = rs.getString("u_salt");
					if(dbPw.equals(SecurityUtils.getEncrypt(param.getU_id(), salt))) {
						param.setU_password(null);
						param.setU_no(rs.getInt("u_no"));
						param.setU_name(rs.getNString("u_name"));
						param.setR_dt(rs.getString("r_dt"));
						param.setM_dt(rs.getString("m_dt"));
						param.setU_profile(rs.getString("u_profile"));
						param.setU_birth(rs.getString("u_birth"));
						param.setChkProfile(param.getU_profile().substring(0, 4));
						param.setGender_name(rs.getInt("gender_no") == 1 ? "여성" : "남성");
						return 1;
					} else {								//로그인 실패.(비밀번호 틀릴 경우)
						return 2;
					}
				}else {			
					System.out.println("아이디 없음");//레코드가 없음. (아이디 없음)
					return 0;						
				}
				
			}	
		});
	}	
	//0:에러발생, 1:로그인 성공, 2:비밀번호 틀림, 3:아이디 없음
	public static int selUser(UserVO param) {
		
		String sql = "SELECT u_no, u_password, u_name, u_birth, gender_no, u_email, u_profile, r_dt, m_dt, u_joinPath, u_salt "
					+ " FROM t_user " 
					+ " WHERE u_id = ? ";
		
		if(param.getU_birth() != null) {
			sql += " AND u_email = ? AND u_birth=? AND gender_no = (Select gender_no from t_gender where gender_name=?) ";
		}
				
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setNString(1,  param.getU_id());
				if(param.getU_birth() != null) {
					ps.setNString(2,  param.getU_email());
					ps.setNString(3,  param.getU_birth());
					ps.setNString(4,  param.getGender_name());
				}
			}

			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				
				if(rs.next()) {					//레코드가 있음
					String dbPw = rs.getNString("u_password");
					String salt = rs.getString("u_salt");
					if(param.getU_birth() == null && dbPw.equals(param.getU_password())) {	//로그인 성공(비밀번호 맞을 경우)
						int i_user = rs.getInt("u_no");
						String nm = rs.getNString("u_name");
						param.setU_password(null);
						param.setU_no(i_user);
						System.out.println("i_user: " + i_user);
						param.setU_name(nm);
						param.setU_birth(rs.getString("u_birth"));
						param.setGender_name(rs.getInt("gender_no") == 1 ? "여성" : "남성");
						param.setU_email(rs.getString("u_email"));
						param.setU_profile(rs.getString("u_profile"));
						param.setR_dt(rs.getString("r_dt"));
						param.setU_joinPath(rs.getInt("u_joinPath"));
						System.out.println("회원가입 직후 r_dt :"+param.getR_dt());
						param.setM_dt(rs.getString("m_dt"));
						System.out.println("회원가입 직후 m_dt :"+param.getM_dt());
						if(param.getU_profile().length() > 4) {
							param.setChkProfile(param.getU_profile().substring(0, 4));
						}
						return 1;
					} else {								//로그인 실패.(비밀번호 틀릴 경우)
						return 2;
					}
				} else {							//레코드가 없음. (아이디 없음)
					return 3;						
				}
				
			}	
		});
	}
	public static int selUpdAfterUser(UserVO param) {
		String sql = "SELECT u_name, u_birth, gender_no, u_email, u_profile, m_dt "
				+ " FROM t_user " 
				+ " WHERE u_id = ? ";
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setNString(1,  param.getU_id());
			}

			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				if(rs.next()) {					//레코드가 있음
					param.setU_name(rs.getNString("u_name"));
					param.setU_birth(rs.getString("u_birth"));
					param.setGender_name(rs.getInt("gender_no") == 1 ? "여성" : "남성");
					param.setU_email(rs.getString("u_email"));
					param.setU_profile(rs.getString("u_profile"));
					param.setM_dt(rs.getString("m_dt"));
					if(param.getU_profile().length() > 4) {
						param.setChkProfile(param.getU_profile().substring(0, 4));
					}
					return 1;
				} else {								//로그인 실패.(비밀번호 틀릴 경우)
					return 2;
				}
			}	
		});
	}
	public static void insU_genre(UserVO param, String str) {
		String sql = "INSERT INTO t_u_genre(u_no, genre_no) VALUES ((select u_no from t_user where u_id=?), (select genre_no from t_genre where genre_name=?))";
		
		JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1,param.getU_id());
				ps.setNString(2,str);
			}
		});
	}
	public static int updUser(UserVO param) {
		StringBuilder sb = new StringBuilder(" UPDATE t_user SET m_dt = now()");
		
		if(param.getU_password() != null) {
			String u_salt = SecurityUtils.generateSalt();
			//param.setU_salt(u_salt);
			param.setU_encrypt(SecurityUtils.getEncrypt(param.getU_password(), u_salt));
			sb.append(" , u_password = '");
			sb.append(param.getU_encrypt());
			sb.append("' ");
			sb.append(" , u_salt = '");
			sb.append(u_salt);
			sb.append("' ");
		}
		if(param.getU_name() != null) {
			sb.append(" , u_name = '");
			sb.append(param.getU_name());
			sb.append("' ");
		}
		if(param.getU_email() != null) {
			sb.append(" , u_email = '");
			sb.append(param.getU_email());
			sb.append("' ");
		}
		if(param.getU_profile() != null) {
			sb.append(" , u_profile = '");
			sb.append(param.getU_profile());
			sb.append("' ");
		}
		if(param.getU_birth() != null) {
			sb.append(" , u_birth = '");
			sb.append(param.getU_birth());
			sb.append("' ");
		}
		
		if(param.getU_no() != 0) {
			sb.append(" where u_no = ");
			sb.append(param.getU_no());
		} else {
			sb.append(" where u_id = '" );
			sb.append(param.getU_id()+ "' ");
		}
		System.out.println("sb : " + sb.toString());
		
		return JdbcTemplate.executeUpdate(sb.toString(), new JdbcUpdateInterface() {
			@Override
			public void update(PreparedStatement ps) throws SQLException {
			}
		});
	}
	
	public static int insFavorite(WebtoonCmtVO param) {
		String sql = " INSERT INTO t_webtoon_favorite "
				+ " (w_no, u_no) "
				+ " VALUES "
				+ " (?, ?) ";
		
		JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getW_no());
				ps.setInt(2, param.getU_no());
			}
		});
		return 1;
	}
	
	public static int delFavorite(WebtoonCmtVO param) {
		String sql = " DELETE FROM t_webtoon_favorite "
				+ " WHERE w_no = ? AND u_no = ? ";
		
		JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getW_no());
				ps.setInt(2, param.getU_no());
			}
		});
		return 1;
	}
}
