package dompoo.study.strategyPattern.quack;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuackNoWay implements QuackBehavior {

    @Override
    public void quack() {
        log.info("소리를 내지 못합니다.");
    }
}
