package com.marespinos.isotherm.infrastructure.external.temperaturereader;

import com.marespinos.isotherm.application.services.temperatureReader.TemperatureReader;
import com.marespinos.isotherm.domain.TemperatureInstantData;
import com.marespinos.isotherm.infrastructure.repository.TemperatureSnapshotEntity;
import com.marespinos.isotherm.infrastructure.repository.TemperatureSnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TemperatureReaderAdaptor implements TemperatureReader {
    Logger logger = LoggerFactory.getLogger(TemperatureReaderAdaptor.class);

    @Value("${w1devicesdirectory}")
    private String W1_DEVICES_DIRECTORY;

    private final TemperatureSnapshotRepository temperatureSnapshotRepository;
    private final TemperatureReaderFromDisk temperatureReaderFromDisk;

    public TemperatureReaderAdaptor(TemperatureSnapshotRepository temperatureSnapshotRepository, TemperatureReaderFromDisk temperatureReaderFromDisk) {
        this.temperatureSnapshotRepository = temperatureSnapshotRepository;
        this.temperatureReaderFromDisk = temperatureReaderFromDisk;
    }

    @Override
    public TemperatureReadingResponse getTemperature() throws IOException {
        Stream<Double> reading = readTemperature();

        RawTemperaturesDto rawData = new RawTemperaturesDto(reading.collect(Collectors.toList()));
        logger.trace("Temperature reading {}", rawData.getRawTemperatures().stream().map(Object::toString).collect(Collectors.joining(", ")));

        TemperatureInstantData temperatures = TemperatureInstantData.of(rawData.getRawTemperatures());
        saveSnapshot(temperatures);
        return TemperatureReadingResponse.of(temperatures);
    }

    private void saveSnapshot(TemperatureInstantData temperatures) {
        TemperatureSnapshotEntity temperatureSnapshot = new TemperatureSnapshotEntity();
        temperatureSnapshot.setDate(new Date());
        temperatureSnapshot.setReading1(temperatures.getRawTemperatures().get(0));
        temperatureSnapshot.setReading2(temperatures.getRawTemperatures().get(1));
        temperatureSnapshot.setMean(temperatures.getMean());
        temperatureSnapshotRepository.save(temperatureSnapshot);
    }

    private Stream<Double> readTemperature() throws IOException {
        return temperatureReaderFromDisk.readTemperature(W1_DEVICES_DIRECTORY);
    }

    private class RawTemperaturesDto {
        private final List<Double> temperatures;

        protected RawTemperaturesDto(List<Double> temperatures) {
            this.temperatures = temperatures;
        }

        public List<Double> getRawTemperatures() {
            return temperatures;
        }
    }
}
