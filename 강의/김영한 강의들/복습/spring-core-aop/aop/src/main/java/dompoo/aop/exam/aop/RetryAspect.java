package dompoo.aop.exam.aop;

import dompoo.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Slf4j
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} annotation={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();
        Throwable exceptionHolder = null;

        for (int retryCount = 0; retryCount < maxRetry; retryCount++) {
            log.info("[retry] tryCount={}/{}", retryCount, maxRetry);
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                exceptionHolder = e;
            }
        }

        throw exceptionHolder;
    }
}
