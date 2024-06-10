package kz.sultan.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kz.sultan.model.Measurement;
import kz.sultan.repositories.MeasurementsRepository;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
	
	private final MeasurementsRepository measurementRepository;
	
	@Autowired
	public MeasurementsService(MeasurementsRepository measurementRepository) {
		this.measurementRepository = measurementRepository;
	}
	
	
	
	public List<Measurement> findAll() {
		return measurementRepository.findAll();
	}
	
	public List<Measurement> findByRaining(boolean raining) {
		return measurementRepository.findByRaining(raining);
	}
	
	@Transactional
	public void save(Measurement measurement) {
		enrichMeasurement(measurement);
		
		measurementRepository.save(measurement);
	}
	private void enrichMeasurement(Measurement measurement) {
		measurement.setCreatedAt(LocalDateTime.now());
	}
}
