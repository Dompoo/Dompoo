package dompoo.jdbc.service;

import dompoo.jdbc.domain.Member;
import dompoo.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 추가
  */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {
	
	private final DataSource dataSource;
	private final MemberRepositoryV2 memberRepository;
	
	public void accountTransfer(String fromId, String toId, int money) throws SQLException, InterruptedException {
		Connection con = dataSource.getConnection();
		
		try {
			con.setAutoCommit(false); // 트랜잭션 시작
			logic(con, fromId, toId, money); // 비즈니스 로직
			con.commit(); // 성공시 커밋
		} catch (Exception e) {
			con.rollback(); // 실패시 롤백
			throw new IllegalStateException(e);
		} finally {
			releaseConnection(con);
		}
	}
	
	private void logic(Connection con, String fromId, String toId, int money) throws SQLException {
		Member fromMember = memberRepository.findById(con, fromId);
		Member toMember = memberRepository.findById(con, toId);
		memberRepository.update(con, fromId, fromMember.getMoney() - money);
		validation(toMember);
		memberRepository.update(con, toId, toMember.getMoney() + money);
	}
	
	private static void releaseConnection(Connection con) {
		if (con != null) {
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (Exception e) {
				log.error("error", e);
			}
		}
	}
	
	private static void validation(Member toMember) {
		if (toMember.getMemberId().equals("ex")) {
			throw new IllegalStateException("이체중 문제 발생!");
		}
	}
	
	
}
