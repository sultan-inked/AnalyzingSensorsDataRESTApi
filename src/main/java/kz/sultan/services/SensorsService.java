package kz.sultan.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kz.sultan.model.Sensor;
import kz.sultan.repositories.SensorsRepository;
import kz.sultan.util.SensorNotFoundException;

@Service
@Transactional(readOnly = true)
public class SensorsService {
	
	private final SensorsRepository sensorsRepository;
	
	@Autowired
	public SensorsService(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}
	
	public List<Sensor> findAll() { return sensorsRepository.findAll(); }
	
	public Sensor findOne(int id) {
		Optional<Sensor> foundSensor = sensorsRepository.findById(id);
		//return foundSensor.orElse(null);
		return foundSensor.orElseThrow(SensorNotFoundException::new);
	}
	
	@Transactional
	public void save(Sensor sensor) {
		enrichSensor(sensor);
		sensorsRepository.save(sensor);
	}
	private void enrichSensor(Sensor sensor) {
		sensor.setCreatedAt(LocalDateTime.now());
		sensor.setUpdatedAt(LocalDateTime.now());
	}
}
