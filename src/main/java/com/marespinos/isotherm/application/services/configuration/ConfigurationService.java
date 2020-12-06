package com.marespinos.isotherm.application.services.configuration;

import java.util.concurrent.CompletableFuture;

public interface ConfigurationService {
    CompletableFuture<ConfigurationData> getConfiguration(Integer id);

    CompletableFuture<Void> setConfiguration(Integer id, Integer minTemp, Integer maxTemp);
}
