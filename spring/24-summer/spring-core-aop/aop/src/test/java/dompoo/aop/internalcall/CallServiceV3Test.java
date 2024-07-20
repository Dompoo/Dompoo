package dompoo.aop.internalcall;

import dompoo.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@Slf4j
@SpringBootTest
class CallServiceV3Test {

    @Autowired CallServiceV3 callService;

    @Test
    void external() {
        callService.external();
    }
}