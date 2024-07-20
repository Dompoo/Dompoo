package dompoo.study.strategyPattern.fly;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlyNoWay implements FlyBehavior {

    @Override
    public void fly() {
        log.info("날지 못합니다.");
    }
}
