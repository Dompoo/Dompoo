package dompoo.study.strategyPattern.quack;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Quack implements QuackBehavior {

    @Override
    public void quack() {
        log.info("큰 소리로 꿕!");
    }
}
