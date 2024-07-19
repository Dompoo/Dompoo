package dompoo.jdbc.repository;

import dompoo.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - TransactionManager
 */
@Slf4j
public class MemberRepositoryV3 {
	
	private final DataSource dataSource;
	
	public MemberRepositoryV3(DataSource dataSource) {
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
//		JdbcUtils.closeConnection(con);
		// 트랜잭션 동기화를 사용할 때는 커넥션 사용후 DataSourceUtils를 통해 반환한다.
		// 해당 커넥션은 커밋이나 롤백전까지 계속 살아있어야 하므로 지우지 않고,
		// 트랜잭션 동기화 매니저가 관리하는 커넥션이 없을 경우 종료한다.
		DataSourceUtils.releaseConnection(con, dataSource);
	}
	
	private Connection getConnection() throws SQLException {
		// 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용한다.
		// 트랜잭션 동기화 매니저가 관리하는 트랜잭션이 없다면 생성하여 반환하고, 있다면 그대로 반환한다.
		Connection connection = DataSourceUtils.getConnection(dataSource);
		log.info("connection = {}", connection);
		return connection;
	}
	
}
