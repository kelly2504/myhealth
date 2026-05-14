package model;

public class Record {
	//a record contains the following: 
	// * weight
	// * temperature
	// * blood pressure
	// * short textual note
	private double weight;
	private double temperature;
	private double blood_pressure;
	private String note;
	
	public Record() {
		
	}
	
	public Record(double weight, double temperature, double blood_pressure, String note) {
		this.weight = weight;
		this.temperature = temperature;
		this.blood_pressure = blood_pressure;
		this.note = note;
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
}
