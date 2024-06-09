package kz.sultan.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import kz.sultan.dto.SensorDTO;
import kz.sultan.model.Sensor;
import kz.sultan.services.SensorsService;
import kz.sultan.util.SensorErrorResponse;
import kz.sultan.util.SensorNotCreatedException;
import kz.sultan.util.SensorValidator;

@Controller
@RequestMapping("/sensors")
public class SensorsController {
	
	private final SensorsService sensorsService;
	private final ModelMapper modelMapper;
	private final SensorValidator sensorValidator;
	
	@Autowired
	public SensorsController(SensorsService sensorsService, ModelMapper modelMapper, SensorValidator sensorValidator) {
		this.sensorsService = sensorsService;
		this.modelMapper = modelMapper;
		this.sensorValidator = sensorValidator;
	}
	
	@PostMapping("/registration")
	public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
			BindingResult bindingResult) {
		Sensor sensor = convertToSensor(sensorDTO);
		
		sensorValidator.validate(sensor, bindingResult);
		
		if(bindingResult.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder();
			
			List<FieldError> errors = bindingResult.getFieldErrors();
			for(FieldError error : errors) {
				errorMessage.append(error.getField())
							.append(" - ")
							.append(error.getDefaultMessage())
							.append(";");
			}
			throw new SensorNotCreatedException(errorMessage.toString());
		}
		
		sensorsService.save(sensor);
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	private Sensor convertToSensor(SensorDTO sensorDTO) {
		return modelMapper.map(sensorDTO, Sensor.class);
	}
	
	@ExceptionHandler
	private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException exc) {
		SensorErrorResponse response = new SensorErrorResponse(
				exc.getMessage(),
				System.currentTimeMillis()
		);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
