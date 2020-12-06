package com.marespinos.isotherm.application.services.pluginteractor;

public interface PlugInteractor {
    void turnSwitchOn();
    void turnSwitchOff();
    Boolean retrieveSwitchStatus();
}
