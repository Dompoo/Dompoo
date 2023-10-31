package dompoo.study.strategyPattern;

import dompoo.study.strategyPattern.fly.FlyBehavior;
import dompoo.study.strategyPattern.quack.QuackBehavior;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Duck {

    public FlyBehavior flyBehavior;
    public QuackBehavior quackBehavior;

    public void fly() {
        flyBehavior.fly();
    }

    public void quack() {
        quackBehavior.quack();
    }

    public abstract void introduce();

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }
}
