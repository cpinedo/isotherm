package com.marespinos.isotherm.application.gettemperature;

import com.marespinos.isotherm.application.services.temperatureReader.TemperatureReader;
import com.marespinos.isotherm.framework.Handler;
import com.marespinos.isotherm.framework.Request;
import com.marespinos.isotherm.infrastructure.external.temperaturereader.TemperatureReadingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class GetTemperatureHandler implements Handler<GetTemperatureCommand, TemperatureReadingResponse> {
    Logger logger = LoggerFactory.getLogger(GetTemperatureHandler.class);

    private final TemperatureReader temperatureReader;

    public GetTemperatureHandler(TemperatureReader temperatureReader) {
        this.temperatureReader = temperatureReader;
    }

    @Override
    public CompletableFuture<TemperatureReadingResponse> handle(Request command) throws IOException {
        logger.info(command.toString());
        return CompletableFuture.completedFuture(temperatureReader.getTemperature());
    }
}
