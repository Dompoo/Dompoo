package dompoo.advanced.trace.strategy;

import dompoo.advanced.trace.strategy.code.strategy.ContextV1;
import dompoo.advanced.trace.strategy.code.strategy.Strategy;
import dompoo.advanced.trace.strategy.code.strategy.StrategyLogic1;
import dompoo.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultIime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultIime={}", resultTime);
    }

    /**
     * 전략 패턴 사용
     */
    @Test
    void strategyV1() {
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategyLogic1);

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2);

        context1.excute();
        context2.excute();
    }

    @Test
    void strategyV2() {
        Strategy strategy1 = new Strategy() {

            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        ContextV1 context1 = new ContextV1(strategy1);

        Strategy strategy2 = new Strategy() {

            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        ContextV1 context2 = new ContextV1(strategy2);

        context1.excute();
        context2.excute();
    }

    @Test
    void strategyV3() {
        ContextV1 context1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });
        ContextV1 context2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });

        context1.excute();
        context2.excute();
    }

    @Test
    void strategyV4() {

        //인터페이스에 메서드가 1개만 있으면 람다표현식으로 표현할 수 있다.
        ContextV1 context1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
        ContextV1 context2 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));

        context1.excute();
        context2.excute();
    }


}
