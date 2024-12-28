package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

@Slf4j
public class AdvisorTest {

    @Test
    void advisorTest1() {
        ServiceInterface service = new ServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(service);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(
                Pointcut.TRUE, //하나의 포인트컷과
                new TimeAdvice() //하나의 어드바이저로 구성된다.
        );
        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("직접 만든 포인트컷")
    void advisorTest2() {
        ServiceInterface service = new ServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(service);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(
                new MyPointcut(), //하나의 포인트컷과
                new TimeAdvice() //하나의 어드바이저로 구성된다.
        );
        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    static class MyPointcut implements Pointcut {

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MethodMatcher() {
                private String matchName = "save";

                @Override
                public boolean matches(Method method, Class<?> targetClass) {
                    boolean result = method.getName().equals(matchName);
                    log.info("포인트컷 호출 method : {}, targetClass : {}", method.getName(), targetClass);
                    log.info("포인트컷 결과 : {}", result);
                    return result;
                }

                @Override
                public boolean isRuntime() {
                    return false;
                }

                @Override
                public boolean matches(Method method, Class<?> targetClass, Object... args) {
                    return false;
                }
            };
        }

        @Test
        @DisplayName("스프링이 제공하는 포인트컷")
        void advisorTest3() {
            ServiceInterface service = new ServiceImpl();

            ProxyFactory proxyFactory = new ProxyFactory(service);

            NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
            pointcut.setMappedName("save");
            //보통 asspectJ 기반 포인트컷을 사용하게 되지만, 일단은 이런 NameMatch도 존재한다.

            DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(
                    pointcut,
                    new TimeAdvice()
            );
            proxyFactory.addAdvisor(advisor);

            ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

            proxy.save();
            proxy.find();
        }


    }
}
