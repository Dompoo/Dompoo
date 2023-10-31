package dompoo.study.strategyPattern;

import dompoo.study.strategyPattern.fly.FlyWithWing;
import dompoo.study.strategyPattern.quack.Squeak;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OldDuck extends Duck {

    @Override
    public void introduce() {
        log.info("저는 늙은 오리입니다.");
    }

    public OldDuck() {
        flyBehavior = new FlyWithWing();
        quackBehavior = new Squeak();
    }
}
