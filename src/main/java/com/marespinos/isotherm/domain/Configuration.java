package com.marespinos.isotherm.domain;

import com.marespinos.isotherm.domain.valueobjects.TemperatureMax;
import com.marespinos.isotherm.domain.valueobjects.TemperatureMin;

import java.util.Objects;

public class Configuration {
    private final TemperatureMax temperatureMax;
    private final TemperatureMin temperatureMin;

    private Configuration(TemperatureMin temperatureMin, TemperatureMax temperatureMax) {
        Objects.requireNonNull(temperatureMax);
        Objects.requireNonNull(temperatureMin);
        validateConfiguration(temperatureMin, temperatureMax);
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
    }

    private void validateConfiguration(TemperatureMin temperatureMin, TemperatureMax temperatureMax) {
        if(temperatureMax.getTemperature() - temperatureMin.getTemperature() < 0)
            throw new TemperatureConfigurationException("Max temperature should be bigger than min temperature.");
    }

    public static Configuration of(TemperatureMin temperatureMin, TemperatureMax temperatureMax){
        return new Configuration(temperatureMin, temperatureMax);
    }

    public Integer getTemperatureMax() {
        return temperatureMax.getTemperature();
    }

    public Integer getTemperatureMin() {
        return temperatureMin.getTemperature();
    }

    public static class TemperatureConfigurationException extends RuntimeException {
        public TemperatureConfigurationException(String s) {
            super(s);
        }
    }
}
