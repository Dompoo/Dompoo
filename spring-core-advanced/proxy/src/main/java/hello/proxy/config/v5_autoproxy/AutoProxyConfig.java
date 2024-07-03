package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    //스프링의 자동 프록시 생성기가 import 되었다.
    //자동 프록시 생성기는 스프링 빈으로 등록된 모든 Advisor를 조회한다.
    //Pointcut 정보를 통해 객체의 모든 메서드를 확인하여 프록시 적용 대상인지 체크하고,
    //프록시의 메서드가 호출되었을 때 Poincut 정보를 통해 어드바이스를 적용할지 체크한다.
    //즉 Pointcut이 두번쓰이는 것이다. (프록시 생성 단계 / 어드바이스 적용 단계)

//    @Bean
//    public Advisor advisor1(LogTrace logTrace) {
//        //포인트컷
//        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
//        pointcut.setMappedNames("request*", "order*", "save*");
//
//        //어드바이스
//        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
//
//        return new DefaultPointcutAdvisor(pointcut, advice);
//    }

//    @Bean
//    public Advisor advisor2(LogTrace logTrace) {
//        //포인트컷
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression("execution(* hello.proxy.app..*(..))");
//        //모든 것을 반환하는
//        //hello.proxy.app 과 그 하위 디렉토리에 존재하는
//        //모든 메서드명
//        //모든 파라미터
//        //에 적용한다는 뜻이다.(aspectJ 표현식을 좀 더 공부하자.)
//
//        //어드바이스
//        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
//
//        return new DefaultPointcutAdvisor(pointcut, advice);
//    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        //포인트컷
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");
        //모든 것을 반환하는
        //hello.proxy.app 과 그 하위 디렉토리에 존재하는
        //모든 메서드명
        //모든 파라미터
        //에 적용하되
        //noLog 메서드명은 제외한다.

        //어드바이스
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
