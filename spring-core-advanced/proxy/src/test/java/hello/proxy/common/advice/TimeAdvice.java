package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        //타겟 클래스의 정보가 MethodInvocation에 모두 녹아있다... 너무 편하다.
        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();
        log.info("resultTime : {}", endTime - startTime);

        return result;
    }
}
