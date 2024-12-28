package dompoo.aop.pointcut;

import dompoo.aop.member.MemberService;
import dompoo.aop.member.annotation.ClassAop;
import dompoo.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberServiceClass={}", memberService.getClass());
        memberService.hello("hello");
    }

    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* dompoo.aop.member..*.*(..))")
        private void allMember() {}

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object args1 = joinPoint.getArgs()[0];
            log.info("[LogArgs1] arg : {}", args1);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[LogArgs2] arg : {}", arg);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg, ..)")
        public Object logArgs3(ProceedingJoinPoint joinPoint, String arg) throws Throwable {
            log.info("[LogArgs3] arg : {}", arg);
            return joinPoint.proceed();
        }

        //프록시 객체 전달
        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this]{} obj : {}", joinPoint.getSignature(), obj.getClass());
        }

        //대상 객체 전달
        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[target]{} obj : {}", joinPoint.getSignature(), obj.getClass());
        }

        //어노테이션 전달
        @Before("allMember() && @target(annotation)")
        public void AtTargetArgs(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target]{} obj : {}", joinPoint.getSignature(), annotation);
        }

        //어노테이션 전달
        @Before("allMember() && @within(annotation)")
        public void AtWithinArgs(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within]{} obj : {}", joinPoint.getSignature(), annotation);
        }

        //메서드의 어노테이션 전달, 값 전달
        @Before("allMember() && @annotation(annotation)")
        public void AtAnnotationArgs(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation]{} value : {}", joinPoint.getSignature(), annotation.value());
        }

    }
}
