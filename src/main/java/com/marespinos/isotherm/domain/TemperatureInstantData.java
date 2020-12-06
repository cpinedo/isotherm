package com.marespinos.isotherm.domain;

import java.util.List;

public class TemperatureInstantData {

    private final List<Double> temperatures;
    private final Double mean;

    private TemperatureInstantData(List<Double> temperatures) {
        this.temperatures = temperatures;
        mean = temperatures.stream().mapToDouble(Double::doubleValue).average().orElse(-1);
    }

    public static TemperatureInstantData of(List<Double> rawData) {
        return new TemperatureInstantData(rawData);
    }

    public List<Double> getRawTemperatures() {
        return temperatures;
    }

    public Double getMean() {
        return mean;
    }
}
