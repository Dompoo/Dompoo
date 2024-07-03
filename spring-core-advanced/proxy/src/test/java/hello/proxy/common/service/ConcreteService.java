package hello.proxy.common.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteService {

    public void save() {
        log.info("ConcreteService.save 호출");
    }
}
