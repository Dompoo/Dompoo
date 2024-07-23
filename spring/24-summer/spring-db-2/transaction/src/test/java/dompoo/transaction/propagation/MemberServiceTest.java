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
    
}