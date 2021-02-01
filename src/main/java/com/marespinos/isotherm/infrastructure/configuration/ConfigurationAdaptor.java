package com.marespinos.isotherm.infrastructure.configuration;

import com.marespinos.isotherm.application.services.configuration.ConfigurationData;
import com.marespinos.isotherm.application.services.configuration.ConfigurationService;
import com.marespinos.isotherm.domain.Configuration;
import com.marespinos.isotherm.domain.valueobjects.TemperatureMax;
import com.marespinos.isotherm.domain.valueobjects.TemperatureMin;
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
        Configuration domainConfiguration = Configuration.of(TemperatureMin.of(data.get().getTemperatureMin()), TemperatureMax.of(data.get().getTemperatureMax()));
        return CompletableFuture.completedFuture(ConfigurationResponse.of(domainConfiguration));
    }

    @Override
    public CompletableFuture<Void> setConfiguration(Integer id, Configuration configuration) {
        ConfigurationEntity entity = new ConfigurationEntity();
        entity.setId(id);
        entity.setTemperatureMin(configuration.getTemperatureMin());
        entity.setTemperatureMax(configuration.getTemperatureMax());
        configurationRepository.save(entity);
        return CompletableFuture.completedFuture(null);
    }
}
