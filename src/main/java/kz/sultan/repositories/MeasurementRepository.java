package kz.sultan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import kz.sultan.model.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

}
