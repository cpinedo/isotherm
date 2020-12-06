package com.marespinos.isotherm.application.setconfiguration;

import com.marespinos.isotherm.framework.Request;

public class SetConfigurationCommand extends Request  {
    private final Integer minTemp;
    private final Integer maxTemp;

    public SetConfigurationCommand(Integer minTemp, Integer maxTemp) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public Integer getMinTemp() {
        return minTemp;
    }

    public Integer getMaxTemp() {
        return maxTemp;
    }

    @Override
    public String toString() {
        return "SetConfigurationCommand{" +
                "minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                '}';
    }
}
