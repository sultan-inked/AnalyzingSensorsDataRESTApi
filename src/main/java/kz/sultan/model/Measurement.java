package kz.sultan.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "Measurement")
public class Measurement {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Min(value = -100, message = "Value should be between -100 and 100")
	@Max(value = 100, message = "Value should be between -100 and 100")
	private double value;
	
	@ManyToOne
	@JoinColumn(name = "sensor_name", referencedColumnName = "name")
	private Sensor sensor;
	
	public Measurement() {}
	
	public Measurement(int id, double value, Sensor sensor) {
		this.id = id;
		this.value = value;
		this.sensor = sensor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
	
	
}
