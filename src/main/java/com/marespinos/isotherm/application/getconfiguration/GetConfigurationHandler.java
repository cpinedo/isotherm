package com.marespinos.isotherm.application.getconfiguration;

import com.marespinos.isotherm.application.gettemperature.GetTemperatureCommand;
import com.marespinos.isotherm.application.services.configuration.ConfigurationData;
import com.marespinos.isotherm.application.services.configuration.ConfigurationService;
import com.marespinos.isotherm.framework.Handler;
import com.marespinos.isotherm.framework.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class GetConfigurationHandler implements Handler<GetTemperatureCommand, ConfigurationData> {
    Logger logger = LoggerFactory.getLogger(GetConfigurationHandler.class);

    private final ConfigurationService configurationService;

    public GetConfigurationHandler(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Override
    public CompletableFuture<ConfigurationData> handle(Request command) throws IOException {
        logger.info(command.toString());
        return configurationService.getConfiguration(1);
    }
}
