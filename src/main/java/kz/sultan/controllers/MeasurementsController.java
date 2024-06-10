package kz.sultan.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kz.sultan.dto.MeasurementDTO;
import kz.sultan.dto.RainyDaysCountDTO;
import kz.sultan.model.Measurement;
import kz.sultan.services.MeasurementsService;
import kz.sultan.util.MeasurementErrorResponse;
import kz.sultan.util.MeasurementNotCreatedException;
import kz.sultan.util.MeasurementValidator;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
	
	private final MeasurementsService measurementsService;
	private final ModelMapper modelMapper;
	private final MeasurementValidator measurementValidator;
	
	@Autowired
	public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
		this.measurementsService = measurementsService;
		this.modelMapper = modelMapper;
		this.measurementValidator = measurementValidator;
	}
	
	
	// Если не указывать @RestController, то придется писать такой метод, который возвращает ResponseEntity
	//@GetMapping
	//public ResponseEntity<List<MeasurementDTO>> getMeasurements() {
	//	System.out.println("**************88");
	//	List<MeasurementDTO> measurementDTOs = measurementsService.findAll().stream().map(this::convertToMeasurementDTO)
	//			.collect(Collectors.toList());
	//	return ResponseEntity.ok(measurementDTOs);
	//}
	@GetMapping
	public List<MeasurementDTO> getMeasurements() {
		return measurementsService.findAll().stream().map(this::convertToMeasurementDTO)
				.collect(Collectors.toList());
	}
	private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
		return modelMapper.map(measurement, MeasurementDTO.class);
	}
	
	@GetMapping("/rainyDaysCount")
	public RainyDaysCountDTO getRainyDaysCount() {
		List<Measurement> measurements = measurementsService.findByRaining(true);
		RainyDaysCountDTO rainyDays = new RainyDaysCountDTO(measurements.size());
		return rainyDays;
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
		
		measurementsService.save(measurement);
		
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
