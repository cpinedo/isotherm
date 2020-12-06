package com.marespinos.isotherm.domain;

public class Configuration {
    private final Integer temperatureMax;
    private final Integer temperatureMin;

    private Configuration(Integer temperatureMax, Integer temperatureMin) {
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
    }

    public static Configuration of(Integer temperatureMax, Integer temperatureMin){
        return new Configuration(temperatureMax, temperatureMin);
    }

    public Integer getTemperatureMax() {
        return temperatureMax;
    }

    public Integer getTemperatureMin() {
        return temperatureMin;
    }
}
