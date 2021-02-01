package com.marespinos.isotherm.domain;

import com.marespinos.isotherm.domain.valueobjects.MeanTemperature;
import com.marespinos.isotherm.domain.valueobjects.TemperatureReadingsList;

import java.util.List;

public class TemperatureInstantData {

    private final TemperatureReadingsList temperatures;
    private final MeanTemperature mean;

    private TemperatureInstantData(TemperatureReadingsList temperatures) {
        this.temperatures = temperatures;
        mean = MeanTemperature.of(temperatures);
    }

    public static TemperatureInstantData of(List<Double> rawData) {
        return new TemperatureInstantData(TemperatureReadingsList.of(rawData));
    }

    public List<Double> getRawTemperatures() {
        return temperatures.getList();
    }

    public Double getMean() {
        return mean.getTemperature();
    }
}
