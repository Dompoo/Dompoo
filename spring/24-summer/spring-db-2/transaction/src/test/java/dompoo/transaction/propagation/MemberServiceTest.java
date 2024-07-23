package dompoo.transaction.propagation;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MemberServiceTest {
    
    @Autowired
    MemberService memberService;
    
    @Autowired
    MemberRepository memberRepository;
    
    @Autowired
    LogRepository logRepository;
    
    /*
    MemberService
    MemberRepository    @Transactional
    LogRepository       @Transactional
    둘다 저장
     */
    @Test
    void outerTxOff_success() {
        //given
        String username = "outerTxOff_success";
        
        //when
        memberService.joinV1(username);
        
        //then 둘다 저장
        assertThat(memberRepository.findByUsername(username)).isPresent();
        assertThat(logRepository.findByMessage(username)).isPresent();
    }
    
    /*
    MemberService
    MemberRepository    @Transactional
    LogRepository       @Transactional  Exception 발생
    Log는 롤백, Member만 저장
     */
    @Test
    void outerTxOff_fail() {
        //given
        String username = "outerTxOff_fail_로그예외";
        
        //when
        Assertions.assertThatThrownBy(() ->
                memberService.joinV1(username))
            .isExactlyInstanceOf(RuntimeException.class);
        
        //then 둘다 저장
        assertThat(memberRepository.findByUsername(username)).isPresent();
        assertThat(logRepository.findByMessage(username)).isEmpty();
    }
    
    /*
    MemberService       @Transactional
    MemberRepository
    LogRepository
    둘다 저장
    전체가 하나의 트랜잭션이다.
    하지만, 만약에 다른 서비스(클라이언트)에서 리포지토리만 사용하고 싶은 상황에서는?
    트랜잭션을 적용할 수 없다. -> 트랜잭션 전파를 활용하면?
     */
    @Test
    void singleTx_success() {
        //given
        String username = "singleTx_success";
        
        //when
        memberService.joinV1(username);
        
        //then
        assertThat(memberRepository.findByUsername(username)).isPresent();
        assertThat(logRepository.findByMessage(username)).isPresent();
    }
    
    /*
    MemberService       @Transactional
    MemberRepository    @Transactional
    LogRepository       @Transactional
    */
    @Test
    void outerTxOn_success() {
        //given
        String username = "outerTxOn_success";
        
        //when
        memberService.joinV1(username);
        
        //then
        assertThat(memberRepository.findByUsername(username)).isPresent();
        assertThat(logRepository.findByMessage(username)).isPresent();
    }
    
}