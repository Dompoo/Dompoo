package Dompoo.advanced.app.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

//전략을 파라미터로 전달받음
//이렇게 하면 필요한 코드 조각을 실행시점에 유연하게 전달받을 수 있따.
@Slf4j
public class ContextV2 {

    public void execute(Strategy strategy) {
        long startTime = System.currentTimeMillis();

        //비즈니스 로직 실행
        strategy.call(); //자식 클래스에서 상속받아서 구현하게 된다.

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }
}
