package hello.proxy.pureproxy.concrete_proxy.code;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class TimeProxy extends ConcreteLogic {

    private static final Logger log = LoggerFactory.getLogger(TimeProxy.class);
    private final ConcreteLogic concreteLogic;

    @Override
    public String operation() {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        String result = super.operation();

        long endTime = System.currentTimeMillis();
        log.info("TimeProxy 종료, resultTime : {}", endTime - startTime);

        return result;
    }
}
