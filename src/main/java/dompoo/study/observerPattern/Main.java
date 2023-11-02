package dompoo.study.observerPattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Weather weather = new Weather();

        log.info("기상 디스플레이 추가");
        ForecastDisplay display1 = new ForecastDisplay(weather);

        log.info("온도 변경1");
        weather.setMeasurements(10, 10, 10);
        log.info("온도 변경2");
        weather.setMeasurements(20, 30, 40);

        log.info("온도 디스플레이 추가");
        TemperatureDisplay display2 = new TemperatureDisplay(weather);

        log.info("온도 변경3");
        weather.setMeasurements(40, 20, 30);
    }
}
