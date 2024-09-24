package dompoo.jdbc.service;

import dompoo.jdbc.domain.Member;
import dompoo.jdbc.repository.MemberRepositoryV2;
import dompoo.jdbc.repository.MemberRepositoryV3;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;

import static dompoo.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 기본 동작, 트랜잭션이 없어서 예외 발생
 */
class MemberServiceV3_1Test {
	
	public static final String MEMBER_A = "memberA";
	public static final String MEMBER_B = "memberB";
	public static final String MEMBER_EX = "ex";
	
	private MemberRepositoryV3 memberRepository;
	private MemberServiceV3_1 memberService;
	
	@BeforeEach
	void setUp() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
		memberRepository = new MemberRepositoryV3(dataSource);
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		memberService = new MemberServiceV3_1(transactionManager, memberRepository);
	}
	
	@AfterEach
	void tearDown() throws SQLException {
		memberRepository.deleteAll();
	}
	
	@Test
	@DisplayName("이체 성공")
	void transfer() throws SQLException, InterruptedException {
		// given
		memberRepository.save(new Member(MEMBER_A, 10000));
		memberRepository.save(new Member(MEMBER_B, 10000));
		
		// when
		memberService.accountTransfer(MEMBER_A, MEMBER_B, 2000);
		
		// then
		Member findMemberA = memberRepository.findById(MEMBER_A);
		Member findMemberB = memberRepository.findById(MEMBER_B);
		assertThat(findMemberA.getMoney()).isEqualTo(8000);
		assertThat(findMemberB.getMoney()).isEqualTo(12000);
	}
	
	@Test
	@DisplayName("이체 중 예외 발생")
	void transferFail() throws SQLException {
		// given
		memberRepository.save(new Member(MEMBER_A, 10000));
		memberRepository.save(new Member(MEMBER_EX, 10000));
		
		// when
		assertThatThrownBy(() ->
				memberService.accountTransfer(MEMBER_A, MEMBER_EX, 2000))
				.isExactlyInstanceOf(IllegalStateException.class);
		
		// then
		Member findMemberA = memberRepository.findById(MEMBER_A);
		Member findMemberEX = memberRepository.findById(MEMBER_EX);
		assertThat(findMemberA.getMoney()).isEqualTo(10000);
		assertThat(findMemberEX.getMoney()).isEqualTo(10000);
	}
}