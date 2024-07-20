package dompoo.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

//    private final ApplicationContext applicationContext; // 얘는 너무 커서 부담이 큼
    private final ObjectProvider<CallServiceV2> serviceProvider; // 지연 조회

    public CallServiceV2(ObjectProvider<CallServiceV2> serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public void external() {
        log.info("call external");
        CallServiceV2 service = serviceProvider.getObject();
        service.internal(); //지연 조회한 클래스의 내부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
