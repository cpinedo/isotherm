package com.marespinos.isotherm.infrastructure.rest;

import com.marespinos.isotherm.application.services.temperatureReader.TemperatureReading;

import java.util.List;
import java.util.Objects;

public class TemperatureDto {
    private final List<Double> temperatures;
    private final Double mean;

    public TemperatureDto(List<Double> temperatures, Double mean) {
        this.temperatures = temperatures;
        this.mean = mean;
    }

    public static TemperatureDto of(TemperatureReading source) {
        return new TemperatureDto(source);
    }

    public TemperatureDto(TemperatureReading source) {
        temperatures = source.getRawTemperatures();
        mean = source.getMean();
    }

    public List<Double> getTemperatures() {
        return temperatures;
    }

    public Double getMean() {
        return mean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemperatureDto that = (TemperatureDto) o;
        return Objects.equals(temperatures, that.temperatures) &&
                Objects.equals(mean, that.mean);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperatures, mean);
    }

    @Override
    public String toString() {
        return "TemperatureResponseDto{" +
                "temperatures=" + temperatures +
                ", mean=" + mean +
                '}';
    }
}
