package com.marespinos.isotherm.infrastructure.external.temperaturereader;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
public class TemperatureReaderFromDisk {
    public static final String TEMPERATURE_FILE_NAME = "w1_slave";
    public static final String SENSOR_PREFIX = "28";
    public static final String TEMPERATURE_PATTERN = "t=(\\d+)";

    public Stream<Double> readTemperature(final String w1DevicesDirectory) throws IOException {
        return Files.list(Paths.get(w1DevicesDirectory))
                .filter(java.nio.file.Files::isDirectory)
                .filter(file -> file.getFileName().toString().startsWith(SENSOR_PREFIX))
                .map(path -> path.resolve(TEMPERATURE_FILE_NAME))
                .map(path1 -> {
                    List<String> fileContent;
                    try {
                        fileContent = java.nio.file.Files.readAllLines(path1);
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
}
