package com.marespinos.isotherm.domain.valueobjects;

public class TemperatureMin {
    private final Integer temperature;

    private TemperatureMin(final Integer temperature) {
        this.temperature = temperature;
    }

    public static TemperatureMin of(final Integer temperature){
        return new TemperatureMin(temperature);
    }

    public Integer getTemperature() {
        return temperature;
    }
}
