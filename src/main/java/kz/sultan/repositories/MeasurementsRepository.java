package kz.sultan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import kz.sultan.model.Measurement;

public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {

}
