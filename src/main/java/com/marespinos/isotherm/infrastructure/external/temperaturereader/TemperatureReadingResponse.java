package com.marespinos.isotherm.infrastructure.external.temperaturereader;

import com.marespinos.isotherm.domain.TemperatureInstantData;

import java.util.List;

public class TemperatureReadingResponse {

    private final List<Double> temperatures;
    private final Double mean;

    public TemperatureReadingResponse(List<Double> temperatures, Double mean) {
        this.temperatures = temperatures;
        this.mean = mean;
    }

    static TemperatureReadingResponse of(TemperatureInstantData data) {
        return new TemperatureReadingResponse(data.getRawTemperatures(), data.getMean());
    }

    public List<Double> getRawTemperatures() {
        return temperatures;
    }

    public Double getMean() {
        return mean;
    }
}
