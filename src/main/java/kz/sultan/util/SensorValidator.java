package kz.sultan.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kz.sultan.model.Sensor;
import kz.sultan.services.SensorsService;

@Component
public class SensorValidator implements Validator {
	
	private final SensorsService sensorsService;
	
	@Autowired
	public SensorValidator(SensorsService sensorsService) {
		this.sensorsService = sensorsService;
	}
	
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Sensor.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Sensor sensor = (Sensor) target;
		
		if(sensorsService.findByName(sensor.getName()) != null) {
			errors.rejectValue("name", "", "This name is already taken");
		}
	}
}
