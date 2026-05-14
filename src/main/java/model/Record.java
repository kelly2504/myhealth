package model;

import java.time.LocalDate;

public class Record {
	//a record contains the following: 
	// * weight
	// * temperature
	// * blood pressure
	// * short textual note
	private User user;
	private double weight;
	private double temperature;
	private double blood_pressure;
	private String note;
	private LocalDate date;
	
	public Record() {
		
	}
	
	public Record(double weight, double temperature, double blood_pressure, String note) {
		this.weight = weight;
		this.temperature = temperature;
		this.blood_pressure = blood_pressure;
		this.note = note;
		this.setDate(LocalDate.now());
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
