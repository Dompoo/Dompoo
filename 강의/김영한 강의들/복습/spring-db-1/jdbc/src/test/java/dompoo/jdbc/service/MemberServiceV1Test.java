package dompoo.jdbc.service;

import dompoo.jdbc.domain.Member;
import dompoo.jdbc.repository.MemberRepositoryV1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static dompoo.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 기본 동작, 트랜잭션이 없어서 예외 발생
 */
class MemberServiceV1Test {
	
	public static final String MEMBER_A = "memberA";
	public static final String MEMBER_B = "memberB";
	public static final String MEMBER_EX = "ex";
	
	private MemberRepositoryV1 memberRepository;
	private MemberServiceV1 memberService;
	
	@BeforeEach
	void setUp() {
		memberRepository = new MemberRepositoryV1(new DriverManagerDataSource(URL, USERNAME, PASSWORD));
		memberService = new MemberServiceV1(memberRepository);
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
		
		memberService.accountTransfer(MEMBER_A, MEMBER_B, 2000);
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
		
		assertThatThrownBy(() ->
				memberService.accountTransfer(MEMBER_A, MEMBER_EX, 2000))
				.isExactlyInstanceOf(IllegalStateException.class);
		Member findMemberA = memberRepository.findById(MEMBER_A);
		Member findMemberEX = memberRepository.findById(MEMBER_EX);
		
		assertThat(findMemberA.getMoney()).isEqualTo(8000); // 문제 있는 부분, 10000이어야 한다.
		assertThat(findMemberEX.getMoney()).isEqualTo(10000);
	}
}