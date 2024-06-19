package dompoo.advanced.trace.strategy;

import dompoo.advanced.trace.strategy.code.strategy.ContextV2;
import dompoo.advanced.trace.strategy.code.strategy.StrategyLogic1;
import dompoo.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    /**
     * 전략 패턴 적용 - 파라미터 방식
     */

    @Test
    void strategyV1() {

        ContextV2 context2 = new ContextV2();
        context2.excute(new StrategyLogic1()); //실행시에 파라미터로 전달한다.
        context2.excute(new StrategyLogic2());

    }

    @Test
    void strategyV2() {

        ContextV2 context2 = new ContextV2();
        context2.excute(() -> log.info("비즈니스 로직1 실행")); //익명 클래스 방법
        context2.excute(() -> log.info("비즈니스 로직2 실행"));

    }


}
