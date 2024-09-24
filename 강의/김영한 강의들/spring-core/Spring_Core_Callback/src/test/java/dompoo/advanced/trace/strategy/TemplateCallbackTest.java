package dompoo.advanced.trace.strategy;

import dompoo.advanced.trace.strategy.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateCallbackTest {

    /**
     * 템플릿 콜백 패턴 - 익명 내부 클래스
     */
    @Test
    void callbackV1() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.excute(() -> log.info("비즈니스 로직1 실행"));
        template.excute(() -> log.info("비즈니스 로직2 실행"));
    }
}
