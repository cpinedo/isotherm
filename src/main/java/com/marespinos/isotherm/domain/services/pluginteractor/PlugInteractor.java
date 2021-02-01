package com.marespinos.isotherm.domain.services.pluginteractor;

public interface PlugInteractor {
    void turnSwitchOn();
    void turnSwitchOff();
    Boolean retrieveSwitchStatus();
}
