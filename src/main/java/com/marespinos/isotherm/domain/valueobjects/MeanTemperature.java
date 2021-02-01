package com.marespinos.isotherm.domain.valueobjects;

public class MeanTemperature {
    private final Double temperature;

    private MeanTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public static MeanTemperature of(TemperatureReadingsList readings){
        return new MeanTemperature(readings.getList().stream().mapToDouble(Double::doubleValue).average().orElse(-1));
    }

    public Double getTemperature() {
        return temperature;
    }
}
