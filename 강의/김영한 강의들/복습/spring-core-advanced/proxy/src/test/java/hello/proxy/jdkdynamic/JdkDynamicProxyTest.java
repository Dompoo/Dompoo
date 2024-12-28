package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() {
        AInterface target = new AImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        AInterface proxy = (AInterface) Proxy.newProxyInstance(
                AInterface.class.getClassLoader(),
                new Class[]{AInterface.class},
                handler);

        proxy.call();
        //클라이언트가 동적프록시의 메서드가 호출하고,
        //동적프록시는 handler의 invoke를 호출하고,
        //invoke 메서드가 AImpl.call()을 호출하게 된다.
        log.info("targetClass() : {}", target.getClass());
        log.info("proxyClass() : {}", proxy.getClass());
    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        //TimeInvocatinHandler의 로직 하나만 정의하면,
        //클래스 종류에 상관없이 프록시를 생성하여 적용할 수 있다.
        //이를 통해 프록시를 클래스 개수만큼 만들 필요는 없어진다!

        BInterface proxy = (BInterface) Proxy.newProxyInstance(
                BInterface.class.getClassLoader(),
                new Class[]{BInterface.class},
                handler);

        proxy.call();
        log.info("targetClass() : {}", target.getClass());
        log.info("proxyClass() : {}", proxy.getClass());
    }
}
