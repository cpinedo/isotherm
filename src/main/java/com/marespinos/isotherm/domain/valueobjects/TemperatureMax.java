package com.marespinos.isotherm.domain.valueobjects;

public class TemperatureMax {
    private final Integer temperature;

    private TemperatureMax(final Integer temperature) {
        this.temperature = temperature;
    }

    public static TemperatureMax of(final Integer temperature){
        return new TemperatureMax(temperature);
    }

    public Integer getTemperature() {
        return temperature;
    }
}
