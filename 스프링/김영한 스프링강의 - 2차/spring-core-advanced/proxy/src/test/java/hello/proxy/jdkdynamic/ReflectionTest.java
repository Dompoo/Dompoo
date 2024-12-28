package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void noReflection() {
        Hello target = new Hello();

        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result1={}", result1);
        //공통 로직1 종료

        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result2={}", result2);
        //공통 로직2 종료

        //계속해서 반복되는 부분이 있다.
        //이 부분을 하나의 메서드로 뽑아서 합칠 수는 없을까?
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }

    @Test
    void reflection1() throws Exception {
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target); //target 인스턴스의 callA를 호출한다.

        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);

        //박혀있던 코드를 '문자'로 바꿨다.
        //이는 동적으로 클래스나 메서드 정보를 바꿀 수 있다는 뜻이 된다.
    }

    @Test
    void reflection2() throws Exception {
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        Method methodCallA = classHello.getMethod("callA");
        //리플렉션을 이용해서 메서드가 아니라 메타정보를 통해서 호출한다.
        //-> 메타정보를 통하여 수많은 메서드를 공통화 가능하다.
        dynamicCall(methodCallA, target);

        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws InvocationTargetException, IllegalAccessException {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
    }
}
