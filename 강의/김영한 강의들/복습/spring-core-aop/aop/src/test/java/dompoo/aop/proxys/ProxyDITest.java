package dompoo.aop.proxys;

import dompoo.aop.member.MemberService;
import dompoo.aop.member.MemberServiceImpl;
import dompoo.aop.proxys.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest//(properties = {"spring.aop.proxy-target-class=true"})
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;
    //JDK 동적프록시의 경우 구체클래스 주입이 되지 않는다. (인터페이스 구현)
    //JDK 동적프록시를 생성한 것을 구체클래스로 캐스팅하지 못하기 때문이다.
    //CGLIB프록시는 구체클래스를 extends 하기 때문에 구체클래스 주입이 가능하다.

    //스프링부트 2.0부터는 CGLIB가 기본 사용이기 때문에, 편하게 구체클래스 주입이 가능하다.

    @Test
    void go() {
        log.info("memberService {}", memberService.getClass());
        log.info("memberServiceImpl {}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }
}
