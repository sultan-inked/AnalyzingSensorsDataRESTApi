package kz.sultan.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kz.sultan.model.Measurement;
import kz.sultan.repositories.MeasurementRepository;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
	
	private final MeasurementRepository measurementRepository;
	
	@Autowired
	public MeasurementService(MeasurementRepository measurementRepository) {
		this.measurementRepository = measurementRepository;
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
