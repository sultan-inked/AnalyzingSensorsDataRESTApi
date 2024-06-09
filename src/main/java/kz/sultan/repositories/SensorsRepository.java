package kz.sultan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kz.sultan.model.Sensor;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {

}
