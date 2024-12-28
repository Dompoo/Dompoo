package dompoo.study.strategyPattern.duck;

import dompoo.study.strategyPattern.fly.FlyWithWing;
import dompoo.study.strategyPattern.quack.Quack;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HealthyDuck extends Duck {

    @Override
    public void introduce() {
        log.info("저는 건강한 오리입니다.");
    }

    public HealthyDuck() {
        flyBehavior = new FlyWithWing();
        quackBehavior = new Quack();
    }
}
