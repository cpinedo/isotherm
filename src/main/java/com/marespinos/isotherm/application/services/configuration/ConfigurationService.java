package com.marespinos.isotherm.application.services.configuration;

import com.marespinos.isotherm.domain.Configuration;

import java.util.concurrent.CompletableFuture;

public interface ConfigurationService {
    CompletableFuture<ConfigurationData> getConfiguration(Integer id);

    CompletableFuture<Void> setConfiguration(Integer id, Configuration configuration);
}
