package com.marespinos.isotherm.infrastructure.projectconfiguration;

import com.marespinos.isotherm.application.getconfiguration.GetConfigurationCommand;
import com.marespinos.isotherm.application.getconfiguration.GetConfigurationHandler;
import com.marespinos.isotherm.application.gettemperature.GetTemperatureCommand;
import com.marespinos.isotherm.application.gettemperature.GetTemperatureHandler;
import com.marespinos.isotherm.application.services.configuration.ConfigurationService;
import com.marespinos.isotherm.application.services.temperatureReader.TemperatureReader;
import com.marespinos.isotherm.application.setconfiguration.SetConfigurationCommand;
import com.marespinos.isotherm.application.setconfiguration.SetConfigurationHandler;
import com.marespinos.isotherm.domain.Isotherm;
import com.marespinos.isotherm.framework.CommandExecutor;
import com.marespinos.isotherm.framework.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
public class FrameworkConfiguration {
    Logger logger = LoggerFactory.getLogger(FrameworkConfiguration.class);

    @Value("${the.version}")
    private String version;

    private final TemperatureReader temperatureReader;
    private final ConfigurationService configurationService;

    public FrameworkConfiguration(TemperatureReader temperatureReader, ConfigurationService configurationService) {
        this.temperatureReader = temperatureReader;
        this.configurationService = configurationService;
    }

    @Bean
    CommandExecutor createCommandExecutor() {
        Map<Class<?>, Handler<?, ?>> handlers = new HashMap<>();
        handlers.put(GetTemperatureCommand.class, new GetTemperatureHandler(temperatureReader));
        handlers.put(GetConfigurationCommand.class, new GetConfigurationHandler(configurationService));
        handlers.put(SetConfigurationCommand.class, new SetConfigurationHandler(configurationService));
        return new CommandExecutor(handlers);
    }

    @Bean
    Isotherm createIsotherm() {
        return new Isotherm();
    }

    @Bean
    void printVersion(){
        logger.info("VERSION ==> {}", version);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }
}
