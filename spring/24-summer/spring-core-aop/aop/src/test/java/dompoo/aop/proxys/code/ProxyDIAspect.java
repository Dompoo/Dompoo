package dompoo.aop.proxys.code;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Slf4j
public class ProxyDIAspect {

    @Before("execution(* dompoo.aop..*.*(..))")
    public void doTrace(JoinPoint joinPoint) {
        log.info("proxyDIAdvice {}", joinPoint.getSignature());
    }
}
