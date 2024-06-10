package kz.sultan.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kz.sultan.model.Measurement;
import kz.sultan.model.Sensor;
import kz.sultan.services.SensorsService;

@Component
public class MeasurementValidator implements Validator {
	
	private final SensorsService sensorService;
	
	@Autowired
	public MeasurementValidator(SensorsService sensorService) {
		this.sensorService = sensorService;
	}
	
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Measurement.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Measurement measurement = (Measurement) target;
		Sensor sensor = sensorService.findByName(measurement.getSensor().getName());
		if(sensor == null) {
			errors.rejectValue("sensor", "", "There is no sensor by that name");
		} else {
			measurement.setSensor(sensor);
		}
	}
}
