package dompoo.jdbc.service;

import dompoo.jdbc.domain.Member;
import dompoo.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 매니저
  */
@Slf4j
public class MemberServiceV3_2 {
	
//	private final PlatformTransactionManager transactionManager;
	private final TransactionTemplate transactionTemplate;
	private final MemberRepositoryV3 memberRepository;
	
	public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);;
		this.memberRepository = memberRepository;
	}
	
	public void accountTransfer(String fromId, String toId, int money) {
		// 언체크 예외의 경우 롤백
		// 체크 예외의 경우 커밋
		transactionTemplate.executeWithoutResult((status) -> {
			try {
				logic(fromId, toId, money);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
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