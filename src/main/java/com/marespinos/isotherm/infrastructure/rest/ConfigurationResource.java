package com.marespinos.isotherm.infrastructure.rest;

import com.marespinos.isotherm.application.getconfiguration.GetConfigurationCommand;
import com.marespinos.isotherm.application.services.configuration.ConfigurationData;
import com.marespinos.isotherm.application.setconfiguration.SetConfigurationCommand;
import com.marespinos.isotherm.framework.CommandExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class ConfigurationResource {
    private final CommandExecutor commandExecutor;


    public ConfigurationResource(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping("/configuration")
    public CompletableFuture<ConfigurationDto> getTemperature() {
        return CompletableFuture
                .supplyAsync(GetConfigurationCommand::new)
                .thenCompose(commandExecutor::executeCommand)
                .thenApply(r -> (ConfigurationData) r)
                .thenApply(ConfigurationDto::of);
    }

    @PostMapping("/configuration")
    public CompletableFuture<Void> postTemperature(@RequestBody ConfigurationDto configurationDto) {
        return CompletableFuture
                .supplyAsync(() -> new SetConfigurationCommand(configurationDto.getMinTemperature(), configurationDto.getMaxTemperature()))
                .thenCompose(commandExecutor::executeCommand)
                .thenApply(r -> null);
    }
}
