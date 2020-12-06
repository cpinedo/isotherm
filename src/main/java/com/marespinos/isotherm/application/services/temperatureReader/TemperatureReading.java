package com.marespinos.isotherm.application.services.temperatureReader;

import java.util.List;

public interface TemperatureReading {
    List<Double> getRawTemperatures();
    Double getMean();
}
