package dompoo.jdbc.service;

import dompoo.jdbc.domain.Member;
import dompoo.jdbc.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 예외 누수 문제 해결
 */
@SpringBootTest
@Slf4j
class MemberServiceV4Test {
	
	public static final String MEMBER_A = "memberA";
	public static final String MEMBER_B = "memberB";
	public static final String MEMBER_EX = "ex";
	
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MemberServiceV4 memberService;
	
	@TestConfiguration
	static class testConfig {
		
		private final DataSource dataSource;
		
		public testConfig(DataSource dataSource) {
			this.dataSource = dataSource;
		}
		
		@Bean
		MemberRepository memberRepository() {
			return new MemberRepositoryV5(dataSource);
		}
		@Bean
		MemberServiceV4 memberService() {
			return new MemberServiceV4(memberRepository());
		}
	}
	
	@AfterEach
	void tearDown() {
		memberRepository.deleteAll();
		log.info("is memberRepository proxy? : {}", AopUtils.isAopProxy(memberRepository));
		log.info("is memberService proxy? : {}", AopUtils.isAopProxy(memberService));
	}
	
	@Test
	@DisplayName("이체 성공")
	void transfer() {
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
	void transferFail() {
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