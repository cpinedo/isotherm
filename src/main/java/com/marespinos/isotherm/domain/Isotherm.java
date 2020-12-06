package com.marespinos.isotherm.domain;

import com.marespinos.isotherm.application.services.pluginteractor.PlugInteractor;
import com.marespinos.isotherm.application.services.temperatureReader.TemperatureReading;

public class Isotherm {

    public static final int ONE_HUNDRED = 100;

    public boolean doMaintenance(Configuration configuration, TemperatureReading reading, PlugInteractor plugInteractor, Boolean lastStatus) {
        if (reading.getMean() * ONE_HUNDRED > configuration.getTemperatureMax() && lastStatus)
            plugInteractor.turnSwitchOff();
        else if (reading.getMean() * ONE_HUNDRED < configuration.getTemperatureMin() && !lastStatus)
            plugInteractor.turnSwitchOn();
        else return false;
        return true;
    }
}
