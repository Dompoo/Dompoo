package dompoo.aop.proxyvs;

import dompoo.aop.member.MemberService;
import dompoo.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); //JDK 동적 프록시, 생략 가능

        //프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        log.info("memberServiceProxy = {}", memberServiceProxy.getClass());

        //JDK 동적 프록시를 구체클래스로 캐스팅하면 실패한다.
        //JDK 동적 프록시는 인터페이스를 기반으로 프록시를 생성하기 때문에 구체 클래스가 뭔지도 모른다.
        Assertions.assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });

    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); //CGLIB 프록시

        //프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        log.info("memberServiceProxy = {}", memberServiceProxy.getClass());

        //CGLIB 프록시를 구체클래스로 캐스팅하면 성공한다.
        //CGLIB는 구체 클래스를 기반으로 프록시를 생성하기 때문이다.
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;

    }
}
