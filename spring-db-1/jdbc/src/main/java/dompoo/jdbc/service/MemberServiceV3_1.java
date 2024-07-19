package dompoo.jdbc.service;

import dompoo.jdbc.domain.Member;
import dompoo.jdbc.repository.MemberRepositoryV2;
import dompoo.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 매니저
  */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_1 {
	
	//	private final DataSource dataSource;
	private final PlatformTransactionManager transactionManager;
	// JDBC 사용시 DataSourceTransactionManager 주입
	// JPA 사용시 JPATransactionManager 주입
	private final MemberRepositoryV3 memberRepository;
	
	public void accountTransfer(String fromId, String toId, int money) {
		//트랜잭션 시작
		TransactionStatus status = transactionManager
				.getTransaction(new DefaultTransactionDefinition());
		try {
			logic(fromId, toId, money); // 비즈니스 로직
			transactionManager.commit(status); // 성공시 커밋
		} catch (Exception e) {
			transactionManager.rollback(status); // 실패시 롤백
			throw new IllegalStateException(e);
		}
	}
	
	private void logic(String fromId, String toId, int money) throws SQLException {
		Member fromMember = memberRepository.findById(fromId);
		Member toMember = memberRepository.findById(toId);
		memberRepository.update(fromId, fromMember.getMoney() - money);
		validation(toMember);
		memberRepository.update(toId, toMember.getMoney() + money);
	}
	
	private static void validation(Member toMember) {
		if (toMember.getMemberId().equals("ex")) {
			throw new IllegalStateException("이체중 문제 발생!");
		}
	}
	
	
}
