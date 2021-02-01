package com.marespinos.isotherm.application.setconfiguration;

import com.marespinos.isotherm.application.services.configuration.ConfigurationService;
import com.marespinos.isotherm.domain.Configuration;
import com.marespinos.isotherm.domain.valueobjects.TemperatureMax;
import com.marespinos.isotherm.domain.valueobjects.TemperatureMin;
import com.marespinos.isotherm.framework.Handler;
import com.marespinos.isotherm.framework.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class SetConfigurationHandler implements Handler<SetConfigurationCommand, Void> {
    Logger logger = LoggerFactory.getLogger(SetConfigurationHandler.class);
    private final ConfigurationService configurationService;

    public SetConfigurationHandler(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Override
    public CompletableFuture<Void> handle(Request command) throws IOException {
        logger.info(command.toString());
        SetConfigurationCommand configurationCommand = (SetConfigurationCommand) command;
        Configuration configuration = Configuration.of(TemperatureMin.of(configurationCommand.getMinTemp()), TemperatureMax.of(configurationCommand.getMaxTemp()));
        return configurationService.setConfiguration(1, configuration);
    }

}
