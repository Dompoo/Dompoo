package dompoo.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private final ApplicationContext ac;

    @Autowired
    public CallServiceV1(ApplicationContext ac) {
        this.ac = ac;
    }

    public void external() {
        log.info("call external");
        ac.getBean(CallServiceV1.class).internal();
        //외부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
