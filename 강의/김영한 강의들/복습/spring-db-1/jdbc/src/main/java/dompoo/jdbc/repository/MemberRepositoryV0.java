package dompoo.jdbc.repository;

import dompoo.jdbc.connection.DBConnectionUtil;
import dompoo.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - Driver Manager
 */
@Slf4j
public class MemberRepositoryV0 {
	
	public Member save(Member member) throws SQLException {
		String sql = "insert into member(member_id, money) values (?,?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBConnectionUtil.getConnection(); //DriverManager를 사용하여 connection 획득
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setInt(2, member.getMoney());
			int effectedRow = pstmt.executeUpdate();
			return member;
		} catch (SQLException e) {
			log.error("DB ERROR!!", e);
			throw e;
		} finally {
			// 실제 tcp/ip로 연결하는 것이기 때문에 닫아주어야 한다.
			// 닫을 때는 역순으로 닫아준다.
			// 단, 첫번째 close에서 예외가 발생하더라도 나머지가 닫혀야 하므로 try catch를 또 써야 한다.
			close(con, pstmt, null);
		}
	}
	
	public Member findById(String memberId) throws SQLException {
		String sql = "select * from member where member_id = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBConnectionUtil.getConnection(); //DriverManager를 사용하여 connection 획득
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Member member = new Member();
				member.setMemberId(rs.getString("member_id"));
				member.setMoney(rs.getInt("money"));
				return member;
			} else {
				throw new NoSuchElementException();
			}
		} catch (SQLException e) {
			log.error("DB ERROR!!", e);
			throw e;
		} finally {
			close(con, pstmt, rs);
		}
	}
	
	public int update(String memberId, int money) throws SQLException {
		String sql = "update member set money = ? where member_id = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBConnectionUtil.getConnection(); //DriverManager를 사용하여 connection 획득
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, money);
			pstmt.setString(2, memberId);
			
			int effectedRow = pstmt.executeUpdate();
			return effectedRow;
		} catch (SQLException e) {
			log.error("DB ERROR!!", e);
			throw e;
		} finally {
			close(con, pstmt, null);
		}
	}
	
	public int delete(String memberId) throws SQLException {
		String sql = "delete from member where member_id = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBConnectionUtil.getConnection(); //DriverManager를 사용하여 connection 획득
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			int effectedRow = pstmt.executeUpdate();
			return effectedRow;
		} catch (SQLException e) {
			log.error("DB ERROR!!", e);
			throw e;
		} finally {
			close(con, pstmt, null);
		}
	}
	
	public int deleteAll() throws SQLException {
		String sql = "delete from member";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBConnectionUtil.getConnection(); //DriverManager를 사용하여 connection 획득
			pstmt = con.prepareStatement(sql);
			
			int effectedRow = pstmt.executeUpdate();
			return effectedRow;
		} catch (SQLException e) {
			log.error("DB ERROR!!", e);
			throw e;
		} finally {
			close(con, pstmt, null);
		}
	}
	
	private void close(Connection con, Statement stmt, ResultSet rs) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				log.error("error", e);
			}
		}
		
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				log.error("error", e);
			}
		}
		
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("error", e);
			}
		}
		
	}
	
}
