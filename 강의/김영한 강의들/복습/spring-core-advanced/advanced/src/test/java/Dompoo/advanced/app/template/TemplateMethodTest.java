package Dompoo.advanced.app.template;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    //핵심 로직과 비핵심 로직이 나뉘어있지 않다.
    //비핵심 로직(횡단관심사)를 좀 더 분리할 수 있는 방법이 없을까?
    //비핵심 로직은 변하지 않는 코드이며, 핵심 로직은 여러 클래스에 걸쳐 바뀌게 된다.

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();

        //비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();

        //비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();
        //AbstractTemplate의 execute를 실행하다가,
        //call을 만나면 call은 오버라이딩된 SubClass의 call을 실행하게 된다.
        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }

    @Test
    void templateMethodV2() {

        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        }; //익명 내부 클래스를 사용하는 해당 클래스명은 TemplateMethodTest$1 , $2와 같이 생성된다.

        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        };

        template2.execute();
    }
}
