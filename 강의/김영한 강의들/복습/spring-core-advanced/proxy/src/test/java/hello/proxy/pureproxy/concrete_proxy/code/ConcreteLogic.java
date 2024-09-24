package hello.proxy.pureproxy.concrete_proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteLogic {

    public String operation() {
        log.info("ConcreteLogic.operation");
        return "data";
    }
}
