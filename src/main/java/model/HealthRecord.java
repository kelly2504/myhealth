package model;

import java.time.LocalDate;

public class HealthRecord {
	//a record contains the following: 
	// * weight
	// * temperature
	// * blood pressure
	// * short textual note
	private String username;
	private String record_number;
	private double weight;
	private double temperature;
	private double blood_pressure;
	private String note;
	private LocalDate date;
	
	public HealthRecord() {
		weight = 0.0;
		temperature = 0.0;
		blood_pressure = 0.0;
		note = "Nothing to note";
	}
	
	public HealthRecord(String record_number,String username, LocalDate date, double weight, double temperature, double blood_pressure, String note) {
		this.record_number = record_number;
		this.username = username;
		this.weight = weight;
		this.temperature = temperature;
		this.blood_pressure = blood_pressure;
		this.note = note;
		this.date = date;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getBlood_pressure() {
		return blood_pressure;
	}

	public void setBlood_pressure(double blood_pressure) {
		this.blood_pressure = blood_pressure;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getUser() {
		return username;
	}

	public void setUser(String username) {
		this.username = username;
	}

	public String getRecord_number() {
		return record_number;
	}

	public void setRecord_number(String record_number) {
		this.record_number = record_number;
	}
}
