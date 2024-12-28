package dompoo.aop.proxys;

import dompoo.aop.member.MemberService;
import dompoo.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl(); // 구체클래스, 인터페이스 모두 있다.
        ProxyFactory proxyFactory = new ProxyFactory(target);

        proxyFactory.setProxyTargetClass(false); //JDK 동적 프록시

        //프록시를 인터페이스로 캐스팅 성공
        MemberService proxy = (MemberService) proxyFactory.getProxy();

        //JDK 동적 프록시는 구체클래스로 캐스팅할 수 없다.
        Assertions.assertThatThrownBy(() -> {
            MemberServiceImpl hello = (MemberServiceImpl) proxy;
        }).isInstanceOf(ClassCastException.class);
    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl(); // 구체클래스, 인터페이스 모두 있다.
        ProxyFactory proxyFactory = new ProxyFactory(target);

        proxyFactory.setProxyTargetClass(true); //CGLIB 프록시

        //프록시를 인터페이스로 캐스팅 성공
        MemberService proxy = (MemberService) proxyFactory.getProxy();

        //프록시를 구체클래스로 캐스팅 성공
        MemberServiceImpl hello = (MemberServiceImpl) proxy;
    }
}
