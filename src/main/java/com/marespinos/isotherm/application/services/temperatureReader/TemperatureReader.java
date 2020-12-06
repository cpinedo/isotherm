package com.marespinos.isotherm.application.services.temperatureReader;

import java.io.IOException;

public interface TemperatureReader {
    TemperatureReading getTemperature() throws IOException;
}
