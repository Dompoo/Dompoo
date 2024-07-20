package dompoo.study.observerPattern.observer;

import dompoo.study.observerPattern.subject.Weather;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ForecastDisplay implements Observer, DisplayElement {

    private float temperature;
    private float humidity;
    private float pressure;

    private final Weather weather;

    public ForecastDisplay(Weather weather) {
        this.weather = weather;
        weather.addObserver(this);
    }

    @Override
    public void update() {
        this.temperature = weather.getTemperature();
        this.humidity = weather.getHumidity();
        this.pressure = weather.getPressure();
        display();
    }

    @Override
    public void display() {
        log.info("기상 디스플레이 - 온도 : {} / 습도 : {} / 기압 : {}", temperature, humidity, pressure);
    }
}
