package com.marespinos.isotherm.infrastructure.scheduled;

import com.marespinos.isotherm.application.services.temperatureReader.TemperatureReader;
import com.marespinos.isotherm.domain.Configuration;
import com.marespinos.isotherm.domain.Isotherm;
import com.marespinos.isotherm.domain.TemperatureInstantData;
import com.marespinos.isotherm.domain.services.pluginteractor.PlugInteractor;
import com.marespinos.isotherm.domain.valueobjects.TemperatureMax;
import com.marespinos.isotherm.domain.valueobjects.TemperatureMin;
import com.marespinos.isotherm.infrastructure.external.temperaturereader.TemperatureReadingResponse;
import com.marespinos.isotherm.infrastructure.repository.ConfigurationEntity;
import com.marespinos.isotherm.infrastructure.repository.ConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class ScheduledActioner {
    Logger logger = LoggerFactory.getLogger(ScheduledActioner.class);

    private final TemperatureReader temperatureReader;
    private final ConfigurationRepository configurationRepository;
    private final PlugInteractor plugInteractor;
    private final Isotherm isotherm;
    private Boolean lastStatus;

    public ScheduledActioner(ConfigurationRepository configurationRepository, TemperatureReader temperatureReader, PlugInteractor plugInteractor, Isotherm isotherm) {
        this.configurationRepository = configurationRepository;
        this.temperatureReader = temperatureReader;
        this.plugInteractor = plugInteractor;
        this.isotherm = isotherm;
        this.lastStatus = plugInteractor.retrieveSwitchStatus();
    }

    @Scheduled(fixedDelay = 1000)
    public void adjustTemperatureActuators() throws IOException {
        Optional<ConfigurationEntity> configurationEntity = configurationRepository.findById(1);
        if (!configurationEntity.isPresent())
            //TODO: Use proper exceptions
            throw new RuntimeException();

        Configuration configuration = Configuration.of(TemperatureMin.of(configurationEntity.get().getTemperatureMin()), TemperatureMax.of(configurationEntity.get().getTemperatureMax()));
        TemperatureReadingResponse reading = temperatureReader.getTemperature();

        if (isotherm.doMaintenance(configuration, TemperatureInstantData.of(reading.getRawTemperatures()), plugInteractor, lastStatus))
            lastStatus = !lastStatus;
    }

    @Scheduled(fixedDelay = 30000)
    public void refreshStatus() {
        lastStatus = plugInteractor.retrieveSwitchStatus();
    }
}
