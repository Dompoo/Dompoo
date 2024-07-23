package dompoo.transaction.propagation;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

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
    
    /*
    MemberService       @Transactional
    MemberRepository    @Transactional
    LogRepository       @Transactional  Exception 발생
    트랜잭션 전파에 의해 모든 데이터가 롤백된다.
     */
    @Test
    void outerTxOn_fail() {
        //given
        String username = "outerTxOn_fail_로그예외";
        
        //when
        Assertions.assertThatThrownBy(() ->
                memberService.joinV1(username))
            .isExactlyInstanceOf(RuntimeException.class);
        
        //then 둘다 저장
        assertThat(memberRepository.findByUsername(username)).isEmpty();
        assertThat(logRepository.findByMessage(username)).isEmpty();
    }
    
    /*
    MemberService       @Transactional  Exception 처리
    MemberRepository    @Transactional
    LogRepository       @Transactional  Exception 발생
    rollbackOnly에 의해 예외처리와 상관없이 롤백된다.
    심지어 정상흐름이 아니라 UnexpectedRollbackException 예외까지 터진다.
    
    자세한 흐름
    MemberRepository는 정상적으로 진행
    
    LogRepository에서 예외 발생
    LogRepository의 proxy에서 해당 예외를 확인하고 트랜잭션 매니저에 롤백 요청,
    트랜잭션 매니저는 신규 트랜잭션이 아니므로 트랜잭션 동기화 매니저에 rollbackOnly 설정
    
    MemberService까지 예외 올라옴
    MemberService에서 예외처리, 정상흐름 반환
    MemberService의 proxy에서 정상흐름이므로 트랜잭션 매니저에 커밋 요청,
    트랜잭션 매니저는 신규 트랜잭션 이므로 실제 롤백을 실행해야 한다.
    실제 롤백을 실행하기 앞서, rollbackOnly를 체크, 체크되어 있으므로 롤백하고 UnexpectedRollbackException 예외를 던진다.
    */
    @Test
    void recoverEx_fail() {
        //given
        String username = "recoverEx_fail_로그예외";
        
        //when
        Assertions.assertThatThrownBy(() ->
                memberService.joinV2(username))
            .isExactlyInstanceOf(UnexpectedRollbackException.class);
        
        //then 둘다 저장
        assertThat(memberRepository.findByUsername(username)).isEmpty();
        assertThat(logRepository.findByMessage(username)).isEmpty();
    }
    
}