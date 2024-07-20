package dompoo.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 service;

    @Autowired
    public void setService(CallServiceV1 service) {
        log.info("callServiceV1 setter={}", service.getClass());
        this.service = service;
    }

    public void external() {
        log.info("call external");
        service.internal(); //주입받은 클래스의 내부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
