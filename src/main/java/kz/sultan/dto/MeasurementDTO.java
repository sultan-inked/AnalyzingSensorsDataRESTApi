package kz.sultan.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import kz.sultan.model.Sensor;

public class MeasurementDTO {
	@Min(value = -100, message = "Value should be between -100 and 100")
	@Max(value = 100, message = "Value should be between -100 and 100")
	private double value;
	
	private boolean raining;
	
	private Sensor sensor;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isRaining() {
		return raining;
	}

	public void setRaining(boolean raining) {
		this.raining = raining;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
	
	
}
