package com.marespinos.isotherm.infrastructure.rest;

import com.marespinos.isotherm.application.gettemperature.GetTemperatureCommand;
import com.marespinos.isotherm.framework.CommandExecutor;
import com.marespinos.isotherm.infrastructure.external.temperaturereader.TemperatureReadingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class TemperatureResource {

    private final CommandExecutor commandExecutor;

    public TemperatureResource(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping("/temperature")
    public CompletableFuture<TemperatureDto> getTemperature(){
        return CompletableFuture
                .supplyAsync(GetTemperatureCommand::new)
                .thenCompose(commandExecutor::executeCommand)
                .thenApply(r -> (TemperatureReadingResponse) r)
                .thenApply(TemperatureDto::of);
    }
}
