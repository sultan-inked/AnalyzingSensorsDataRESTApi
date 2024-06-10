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
import kz.sultan.dto.MeasurementDTO;
import kz.sultan.model.Measurement;
import kz.sultan.services.MeasurementService;
import kz.sultan.util.MeasurementErrorResponse;
import kz.sultan.util.MeasurementNotCreatedException;
import kz.sultan.util.MeasurementValidator;

@Controller
@RequestMapping("/measurements")
public class MeasurementController {
	
	private final MeasurementService measurementService;
	private final ModelMapper modelMapper;
	private final MeasurementValidator measurementValidator;
	
	@Autowired
	public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
		this.measurementService = measurementService;
		this.modelMapper = modelMapper;
		this.measurementValidator = measurementValidator;
	}
	
	
	
	@PostMapping("/add")
	public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
			BindingResult bindingResult) {
		Measurement measurement = convertToMeasurement(measurementDTO);
		
		measurementValidator.validate(measurement, bindingResult);
		
		if(bindingResult.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder();
			
			List<FieldError> errors = bindingResult.getFieldErrors();
			for(FieldError error : errors) {
				errorMessage.append(error.getField())
							.append(" - ")
							.append(error.getDefaultMessage())
							.append(";");
			}
			System.out.println(errorMessage.toString());
			throw new MeasurementNotCreatedException(errorMessage.toString());
		}
		System.out.println(measurement.getSensor());
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
		return modelMapper.map(measurementDTO, Measurement.class);
	}
	
	@ExceptionHandler
	private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException exc) {
		MeasurementErrorResponse response = new MeasurementErrorResponse(
				exc.getMessage(),
				System.currentTimeMillis()
		);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
