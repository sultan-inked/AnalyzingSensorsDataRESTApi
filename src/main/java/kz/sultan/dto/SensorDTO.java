package kz.sultan.dto;

import org.hibernate.validator.constraints.NotEmpty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public class SensorDTO {
	@NotEmpty(message = "Name should not be empty")
	@Size(min = 1, max = 100, message = "Name should be between 1 and 100 characters")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
