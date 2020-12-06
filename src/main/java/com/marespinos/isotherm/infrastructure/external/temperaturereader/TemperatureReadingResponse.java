package com.marespinos.isotherm.infrastructure.external.temperaturereader;

import com.marespinos.isotherm.application.services.temperatureReader.TemperatureReading;
import com.marespinos.isotherm.domain.TemperatureInstantData;

import java.util.List;

public class TemperatureReadingResponse implements TemperatureReading {

    private final List<Double> temperatures;
    private final Double mean;

    public TemperatureReadingResponse(List<Double> temperatures, Double mean) {
        this.temperatures = temperatures;
        this.mean = mean;
    }

    static TemperatureReadingResponse of(TemperatureInstantData data){
        return new TemperatureReadingResponse(data.getRawTemperatures(), data.getMean());
    }

    @Override
    public List<Double> getRawTemperatures() {
        return temperatures;
    }

    @Override
    public Double getMean() {
        return mean;
    }
}
