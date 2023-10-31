package dompoo.study.strategyPattern.duck;

import dompoo.study.strategyPattern.fly.FlyNoWay;
import dompoo.study.strategyPattern.quack.QuackNoWay;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ToyDuck extends Duck {

    @Override
    public void introduce() {
        log.info("저는 장난감 오리입니다.");
    }

    public ToyDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new QuackNoWay();
    }
}
