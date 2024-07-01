package Dompoo.advanced.app.strategy;

import Dompoo.advanced.app.strategy.code.strategy.ContextV2;
import Dompoo.advanced.app.strategy.code.strategy.StrategyLogic1;
import Dompoo.advanced.app.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    @Test
    void stategyV1() {
        ContextV2 context = new ContextV2();

        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }

    @Test
    void strategyV2() {
        ContextV2 context = new ContextV2();

        context.execute(() -> log.info("비즈니스 로직1 실행"));
        context.execute(() -> log.info("비즈니스 로직2 실행"));
    }
}
