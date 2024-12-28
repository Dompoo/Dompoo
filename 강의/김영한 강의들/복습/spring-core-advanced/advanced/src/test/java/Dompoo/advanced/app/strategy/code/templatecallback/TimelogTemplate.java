package Dompoo.advanced.app.strategy.code.templatecallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimelogTemplate {

    //Runnable 대신에 커스텀 콜백 인터페이스를 정의해도 좋을 것 같다.
    public void execute(Runnable callback) {
        long startTime = System.currentTimeMillis();

        //비즈니스 로직 실행
        callback.run(); //자식 클래스에서 상속받아서 구현하게 된다.

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }
}
