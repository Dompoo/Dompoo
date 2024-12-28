package dompoo.study.strategyPattern.quack;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Squeak implements QuackBehavior {

    @Override
    public void quack() {
        log.info("작은 소리로 삑.");
    }
}
