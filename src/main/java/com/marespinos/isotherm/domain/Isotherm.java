package com.marespinos.isotherm.domain;

import com.marespinos.isotherm.domain.services.pluginteractor.PlugInteractor;

public class Isotherm {

    public static final int ONE_HUNDRED = 100;

    public boolean doMaintenance(Configuration configuration, TemperatureInstantData reading, PlugInteractor plugInteractor, Boolean lastStatus) {
        if (reading.getMean() * ONE_HUNDRED > configuration.getTemperatureMax() && lastStatus)
            plugInteractor.turnSwitchOff();
        else if (reading.getMean() * ONE_HUNDRED < configuration.getTemperatureMin() && !lastStatus)
            plugInteractor.turnSwitchOn();
        else return false;
        return true;
    }
}
