package com.marespinos.isotherm.infrastructure.configuration;

import com.marespinos.isotherm.application.services.configuration.ConfigurationData;
import com.marespinos.isotherm.domain.Configuration;

public class ConfigurationResponse implements ConfigurationData {
    private final Integer maxTemp;
    private final Integer minTemp;

    public ConfigurationResponse(Integer maxTemp, Integer minTemp) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public static ConfigurationResponse of(Configuration data) {
        return new ConfigurationResponse(data.getTemperatureMax(), data.getTemperatureMin());
    }

    @Override
    public Integer getMaxTemperature() {
        return maxTemp;
    }

    @Override
    public Integer getMinTemperature() {
        return minTemp;
    }
}
