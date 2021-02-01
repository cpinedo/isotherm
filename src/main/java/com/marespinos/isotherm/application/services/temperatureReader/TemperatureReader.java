package com.marespinos.isotherm.application.services.temperatureReader;

import com.marespinos.isotherm.domain.TemperatureInstantData;
import com.marespinos.isotherm.infrastructure.external.temperaturereader.TemperatureReadingResponse;

import java.io.IOException;

public interface TemperatureReader {
    TemperatureReadingResponse getTemperature() throws IOException;
}
