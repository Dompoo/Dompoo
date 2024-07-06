package dompoo.aop.pointcut;

import dompoo.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        //public java.lang.String dompoo.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    void exactMatch() {
        //public java.lang.String dompoo.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String dompoo.aop.member.MemberServiceImpl.hello(String))");
        //execution(접근제어자? 반환타입 패키지?.클래스명.메서드이름(파라미터타입) 예외?)

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch() {
        pointcut.setExpression("execution(* *(..))"); //가장 많이 생략
        //파라미터만 모든 것을 받는다는 뜻을 ..으로 받는다. 개수와 타입이 상관없다는 뜻!

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch1() {
        pointcut.setExpression("execution(* hell*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatc2() {
        pointcut.setExpression("execution(* *ello(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch3() {
        pointcut.setExpression("execution(* *ell*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatch() {
        pointcut.setExpression("execution(String dompoo.aop.member.MemberServiceImpl.hello(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatch1() {
        pointcut.setExpression("execution(String dompoo.aop.member.*.*(..))");
        //패키지안의 타입과 메서드를 *로

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatch2() {
        pointcut.setExpression("execution(String dompoo.aop.member..*.*(..))");
        //패키지에서의 .은 정확히 그 하위
        //패키지에서의 ..은 그 패키지와 그 하위 패키지를 모두 포함

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatch3() {
        pointcut.setExpression("execution(String dompoo.aop..*.*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatch() {
        pointcut.setExpression("execution(String dompoo.aop.member.MemberServiceImpl.*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatch1() {
        pointcut.setExpression("execution(String dompoo.aop.member.MemberService.*(..))");
        //execution은 부모 타입으로도 매칭이 된다.

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        pointcut.setExpression("execution(String dompoo.aop.member.MemberService.*(..))");
        //하지만 부모타입에는 없는 internal 메서드는 매칭이 안된다.
        //부모타입으로 자식타입을 매칭할 때는 부모타입에 선언한 메서드까지만 매칭되는 것이다.

        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);

        Assertions.assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void argsMatch() {
        pointcut.setExpression("execution(* *(String))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsMatch1() {
        pointcut.setExpression("execution(* *())");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void argsMatch2() {
        pointcut.setExpression("execution(* *(*))");
        //타입 상관없는 하나의 파라미터만 허용

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsMatch3() {
        pointcut.setExpression("execution(* *(..))");
        //파라미터의 타입과 개수에 상관 X

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsMatch4() {
        pointcut.setExpression("execution(* *(String, ..))");
        //스트링 1개로 시작하고, 0개 이상의 타입 상관없는 파라미터

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
