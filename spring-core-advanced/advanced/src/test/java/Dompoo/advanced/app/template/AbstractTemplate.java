package Dompoo.advanced.app.template;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {

    public void execute() {
        long startTime = System.currentTimeMillis();

        //비즈니스 로직 실행
        call(); //자식 클래스에서 상속받아서 구현하게 된다.

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    //핵심 로직이 구현될 부분
    protected abstract void call();
}
