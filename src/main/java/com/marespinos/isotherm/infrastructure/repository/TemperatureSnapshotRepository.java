package com.marespinos.isotherm.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureSnapshotRepository extends JpaRepository<TemperatureSnapshotEntity, Integer> {
}
