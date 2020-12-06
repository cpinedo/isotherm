package com.marespinos.isotherm.infrastructure.configuration;

import com.marespinos.isotherm.application.services.configuration.ConfigurationData;
import com.marespinos.isotherm.application.services.configuration.ConfigurationService;
import com.marespinos.isotherm.domain.Configuration;
import com.marespinos.isotherm.infrastructure.repository.ConfigurationEntity;
import com.marespinos.isotherm.infrastructure.repository.ConfigurationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ConfigurationAdaptor implements ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    public ConfigurationAdaptor(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public CompletableFuture<ConfigurationData> getConfiguration(Integer id) {
        Optional<ConfigurationEntity> data = configurationRepository.findById(id);
        Configuration domainConfiguration = Configuration.of(data.get().getTemperatureMax(), data.get().getTemperatureMin());
        return CompletableFuture.completedFuture(ConfigurationResponse.of(domainConfiguration));
    }

    @Override
    public CompletableFuture<Void> setConfiguration(Integer id, Integer minTemp, Integer maxTemp) {
        ConfigurationEntity entity = new ConfigurationEntity();
        entity.setId(id);
        entity.setTemperatureMin(minTemp);
        entity.setTemperatureMax(maxTemp);
        configurationRepository.save(entity);
        return CompletableFuture.completedFuture(null);
    }
}
