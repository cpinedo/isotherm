package com.marespinos.isotherm.domain.valueobjects;

import java.util.Collections;
import java.util.List;

public class TemperatureReadingsList {
    private final List<Double> list;

    private TemperatureReadingsList(List<Double> list) {
        this.list = Collections.unmodifiableList(list);
    }

    public static TemperatureReadingsList of(List<Double> list){
        return new TemperatureReadingsList(list);
    }

    public List<Double> getList() {
        return list;
    }
}
