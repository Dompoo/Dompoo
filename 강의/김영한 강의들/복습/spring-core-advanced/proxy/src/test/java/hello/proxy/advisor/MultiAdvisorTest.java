package hello.proxy.advisor;

import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

@Slf4j
public class MultiAdvisorTest {

    @Test
    @DisplayName("여러 프록시에 각각 어드바이저")
    void multiadvisorTest1() {
        ServiceInterface service = new ServiceImpl();

        ProxyFactory proxyFactory1 = new ProxyFactory(service);
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        proxyFactory1.addAdvisor(advisor1);
        ServiceInterface proxy1 = (ServiceInterface) proxyFactory1.getProxy();

        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1); // <- target 주의
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
        proxyFactory2.addAdvisor(advisor2);
        ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy();

        //이렇게 여러개의 프록시를 적용할 수 있지만, 프록시가 10개라면 10개를 모두 만들어야 한다.
        //스프링은 하나의 프록시에 여러개의 어드바이저를 적용할 수 있또록 돕는다.

        proxy2.save();
    }

    static class Advice1 implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("어드바이스 1 호출");
            return invocation.proceed();
        }
    }

    static class Advice2 implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("어드바이스 2 호출");
            return invocation.proceed();
        }
    }

    @Test
    @DisplayName("하나의 프록시에 여러 어드바이저")
    void multiadvisorTest2() {
        ServiceInterface service = new ServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(service);

        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
        proxyFactory.addAdvisor(advisor2);
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        proxyFactory.addAdvisor(advisor1);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        //중요한 것은 하나의 프록시에! 여러 어드바이저가 적용될 뿐이다.
        //여러 프록시가 생기는 것이 절대 아니다.

        proxy.save();
    }
}
