package dompoo.study.decoratorPattern;

import dompoo.study.decoratorPattern.berverage.Beverage;
import dompoo.study.decoratorPattern.berverage.Espresso;
import dompoo.study.decoratorPattern.decorator.Mocha;
import dompoo.study.decoratorPattern.decorator.Whip;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        Beverage beverage = new Espresso();
        log.info("에스프레소 주문이요! (현재 상태 : {})", beverage.getDescription());

        beverage = new Mocha(beverage);
        log.info("모카 한바퀴랑요. (현재 상태 : {})", beverage.getDescription());
        beverage = new Mocha(beverage);
        log.info("아, 모카 한바퀴 더요. (현재 상태 : {})", beverage.getDescription());
        beverage = new Whip(beverage);
        log.info("휘핑크림 올려주세요. (현재 상태 : {})", beverage.getDescription());

        log.info("네, 가격은 {}원 입니다.", beverage.cost());
    }
}
