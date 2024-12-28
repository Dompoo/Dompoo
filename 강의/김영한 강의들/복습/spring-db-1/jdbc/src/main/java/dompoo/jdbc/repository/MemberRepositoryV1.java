package dompoo.jdbc.repository;

import dompoo.jdbc.connection.DBConnectionUtil;
import dompoo.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DataSource
 */
@Slf4j
public class MemberRepositoryV1 {
	
	private final DataSource dataSource;
	
	public MemberRepositoryV1(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Member save(Member member) throws SQLException {
		String sql = "insert into member(member_id, money) values (?,?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setInt(2, member.getMoney());
			int effectedRow = pstmt.executeUpdate();
			return member;
		} catch (SQLException e) {
			log.error("DB ERROR!!", e);
			throw e;
		} finally {
			close(con, pstmt, null);
		}
	}
	
	public Member findById(String memberId) throws SQLException {
		String sql = "select * from member where member_id = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
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
			con = getConnection();
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
			con = getConnection();
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
			con = getConnection();
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
		JdbcUtils.closeResultSet(rs);
		JdbcUtils.closeStatement(stmt);
		
		// HikariProxyConnection 으로 래핑된 커넥션이기 때문에,
		// 커넥션이 닫히지 않고 풀에 반환된다.
		JdbcUtils.closeConnection(con);
	}
	
	private Connection getConnection() throws SQLException {
		Connection connection = dataSource.getConnection();
		log.info("connection = {}", connection);
		return connection;
	}
	
}
