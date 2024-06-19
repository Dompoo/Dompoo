package dompoo.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstarctTemplate {

    public void execute() {

        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        call(); //자식클래스가 따로 구현하게 만든다.
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultIime={}", resultTime);
    }

    protected abstract void call();
}
