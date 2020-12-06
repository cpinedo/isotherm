package com.marespinos.isotherm.infrastructure.external.temperaturereader;

import com.marespinos.isotherm.application.services.temperatureReader.TemperatureReader;
import com.marespinos.isotherm.application.services.temperatureReader.TemperatureReading;
import com.marespinos.isotherm.domain.TemperatureInstantData;
import com.marespinos.isotherm.infrastructure.repository.TemperatureSnapshotEntity;
import com.marespinos.isotherm.infrastructure.repository.TemperatureSnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TemperatureReaderAdaptor implements TemperatureReader {
    Logger logger = LoggerFactory.getLogger(TemperatureReaderAdaptor.class);

    @Value("${w1devicesdirectory}")
    private String W1_DEVICES_DIRECTORY;

    public static final String TEMPERATURE_FILE_NAME = "w1_slave";
    public static final String SENSOR_PREFIX = "28";
    public static final String TEMPERATURE_PATTERN = "t=(\\d+)";

    private final TemperatureSnapshotRepository temperatureSnapshotRepository;

    public TemperatureReaderAdaptor(TemperatureSnapshotRepository temperatureSnapshotRepository) {
        this.temperatureSnapshotRepository = temperatureSnapshotRepository;
    }

    @Override
    public TemperatureReading getTemperature() throws IOException {
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
        return Files.list(Paths.get(W1_DEVICES_DIRECTORY))
                    .filter(Files::isDirectory)
                    .filter(file -> file.getFileName().toString().startsWith(SENSOR_PREFIX))
                    .map(path -> path.resolve(TEMPERATURE_FILE_NAME))
                    .map(path1 -> {
                        List<String> fileContent;
                        try {
                            fileContent = Files.readAllLines(path1);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return fileContent.get(1);
                    })
                    .map(line -> {
                        Pattern p = Pattern.compile(TEMPERATURE_PATTERN);
                        Matcher m = p.matcher(line);
                        if (m.find()) {
                            return Double.parseDouble(m.group(1));
                        }
                        return Double.parseDouble("-100000");
                    })
                .map(value -> value/1000);
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
