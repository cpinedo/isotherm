package com.marespinos.isotherm.infrastructure.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
public class ConfigurationEntity {
    @Id
    private Integer id;
    private Integer temperatureMax;
    private Integer temperatureMin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Integer temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public Integer getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Integer temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurationEntity that = (ConfigurationEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(temperatureMax, that.temperatureMax) &&
                Objects.equals(temperatureMin, that.temperatureMin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, temperatureMax, temperatureMin);
    }
}
