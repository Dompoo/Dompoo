package dompoo.study.observerPattern.observer;

import dompoo.study.observerPattern.subject.Weather;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemperatureDisplay implements Observer, DisplayElement {

    private float temperature;
    private final Weather weather;

    public TemperatureDisplay(Weather weather) {
        this.weather = weather;
        weather.addObserver(this);
    }

    @Override
    public void display() {
        log.info("온도 디스플레이 - 온도 : {}", temperature);
    }

    @Override
    public void update() {
        this.temperature = weather.getTemperature();
        display();
    }
}
