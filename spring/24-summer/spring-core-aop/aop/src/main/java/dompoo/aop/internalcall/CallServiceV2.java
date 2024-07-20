package dompoo.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

    private final ObjectProvider<CallServiceV2> provider;

    @Autowired
    public CallServiceV2(ObjectProvider<CallServiceV2> provider) {
        this.provider = provider;
    }

    public void external() {
        log.info("call external");
        provider.getObject().internal();
        //외부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
