package dompoo.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

//    @Around("dompoo.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //@Before
            Object result = joinPoint.proceed();
            //@After returning
            return result;
        } catch (Exception e) {
            //@After throwing
            throw e;
        } finally {
            //@After
        }
    }

    @Before("dompoo.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        //파라미터는 쓰지 않으면 빼도 된다.
        log.info("BEFORE={}", joinPoint.getSignature());
    }

    @AfterReturning(value = "dompoo.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doAfterReturn(JoinPoint joinPoint, Object result) {
        //returning 타입으로 반환하는 메서드만 호출된다.
        log.info("AFTER_RETURNING={}, result={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "dompoo.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doAfterThrow(JoinPoint joinPoint, Exception ex) {
        log.info("AFTER_THROWING={}, exception={}", joinPoint.getSignature(), ex);
    }

    @After("dompoo.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("AFTER={}", joinPoint.getSignature());
    }

}
