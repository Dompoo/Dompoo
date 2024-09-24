package dompoo.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LogRepository logRepository;

    /**
     * memberService    @Transactional : OFF
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON
     */
    @Test
    void outerTxOff_success() {
        //given
        String username = "outerTxOff_success";

        //when
        memberService.joinV1(username);

        //then : 모든 데이터가 정상 저장된다.
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService    @Transactional : OFF
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON     !Exception
     */
    @Test
    void outerTxOff_fail() {
        //given
        String username = "로그예외_outerTxOff_fail";

        //when
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        //then : 로그 데이터만이 롤백 된다. (각각 Tx)
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : OFF
     * logRepository    @Transactional : OFF
     * 트랜잭션을 따로 설정하려면 트랜잭션이 적용된 메서드와 아닌 메서드를 구분해야 한다. -> 번거롭다.
     */
    @Test
    void singleTx() {
        //given
        String username = "outerTxOff_success";

        //when
        memberService.joinV1(username);

        //then : 모든 데이터가 정상 저장된다.
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON
     */
    @Test
    void outerTxOn_success() {
        //given
        String username = "outerTxOn_success";

        //when
        memberService.joinV1(username);

        //then : 모든 데이터가 정상 저장된다.
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON Exception
     */
    @Test
    void outerTxOn_fail() {
        //given
        String username = "로그예외_outerTxOn_fail";

        //when
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        //then : 모든 데이터가 전체 롤백 되어야 한다. -> 데이터 정합성 문제 X
        assertTrue(memberRepository.find(username).isEmpty());
        assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON Exception
     * 로그 예외가 터지더라도 서비스에서 예외를 잡으면 회원은 정상 저장될 수 있을까?
     * X -> rollbackOnly가 이미 체크되어 있으므로 트랜잭션 매니저는 unexcpectedRollbackException을 던진다.
     */
    @Test
    void recoverException_fail() {
        //given
        String username = "로그예외_recoverException_fail";

        //when
        assertThatThrownBy(() -> memberService.joinV2(username))
                .isInstanceOf(UnexpectedRollbackException.class);

        //then : 모든 데이터가 롤백되어 버린다.
        assertTrue(memberRepository.find(username).isEmpty());
        assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * memberService    @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository    @Transactional : ON(REQUIRES_NEW) Exception
     * REQUIRES_NEW옵션을 통해 별도의 트랜잭션 영역으로 설정하여 서비스와 멤버의 트랜잭션에 영향을 주지 않게 한다.
     */
    @Test
    void recoverException_success() {
        //given
        String username = "로그예외_recoverException_success";

        //when
        memberService.joinV2(username);

        //then : member 저장, log 롤백
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isEmpty());
    }

}