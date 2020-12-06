package com.marespinos.isotherm.infrastructure.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class TemperatureSnapshotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;
    private Double reading1;
    private Double reading2;
    private Double mean;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getReading1() {
        return reading1;
    }

    public void setReading1(Double reading1) {
        this.reading1 = reading1;
    }

    public Double getReading2() {
        return reading2;
    }

    public void setReading2(Double reading2) {
        this.reading2 = reading2;
    }

    public Double getMean() {
        return mean;
    }

    public void setMean(Double mean) {
        this.mean = mean;
    }
}
