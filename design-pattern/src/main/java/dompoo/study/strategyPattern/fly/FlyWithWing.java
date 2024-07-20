package dompoo.study.strategyPattern.fly;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlyWithWing implements FlyBehavior {

    @Override
    public void fly() {
        log.info("날개로 날고 있습니다!");
    }
}
