package Dompoo.advanced.app.strategy.code;

import Dompoo.advanced.app.strategy.code.templatecallback.TimelogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateCallbackTest {

    @Test
    void callBackV1() {
        TimelogTemplate template = new TimelogTemplate();

        template.execute(() -> log.info("비즈니스 로직1 실행"));
        template.execute(() -> log.info("비즈니스 로직2 실행"));
    }
}
