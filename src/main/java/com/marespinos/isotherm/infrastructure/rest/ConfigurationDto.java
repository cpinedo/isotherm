package com.marespinos.isotherm.infrastructure.rest;

import com.marespinos.isotherm.application.services.configuration.ConfigurationData;

import java.util.Objects;

public class ConfigurationDto {
    private Integer maxTemperature;
    private Integer minTemperature;

    public ConfigurationDto(Integer maxTemperature, Integer minTemperature) {
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Integer maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Integer getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Integer minTemperature) {
        this.minTemperature = minTemperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurationDto that = (ConfigurationDto) o;
        return Objects.equals(maxTemperature, that.maxTemperature) &&
                Objects.equals(minTemperature, that.minTemperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxTemperature, minTemperature);
    }

    @Override
    public String toString() {
        return "ConfigurationDto{" +
                "maxTemperature=" + maxTemperature +
                ", minTemperature=" + minTemperature +
                '}';
    }

    public static ConfigurationDto of(ConfigurationData configurationData) {
        return new ConfigurationDto(configurationData.getMaxTemperature(), configurationData.getMinTemperature());
    }
}
