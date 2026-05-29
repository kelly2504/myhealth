package model;

import java.time.LocalDate;

public class HealthRecord {
	//a record contains the following: 
	// * weight
	// * temperature
	// * blood pressure
	// * short textual note
	private String username;
	private String recordNumber;
	private double weight;
	private double temperature;
//	private double bloodPressure;
	private int systolic;
	private int diastolic;
	private String note;
	private LocalDate date;
	
	public HealthRecord() {
		weight = 0.0;
		temperature = 0.0;
//		bloodPressure = 0.0;
		systolic = 0;
		diastolic = 0;
		note = "Nothing to note";
	}
	
	public HealthRecord(String recordNumber,String username, LocalDate date, double weight, double temperature,int systolic, int diastolic, String note) {
		this.recordNumber = recordNumber;
		this.username = username;
		this.weight = weight;
		this.temperature = temperature;
//		this.bloodPressure = bloodPressure;
		this.systolic = systolic;
		this.diastolic = diastolic;
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

//	public double getBloodPressure() {
//		return bloodPressure;
//	}
//
//	public void setBloodPressure(double bloodPressure) {
//		this.bloodPressure = bloodPressure;
//	}

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
		return recordNumber;
	}

	public void setRecord_number(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	public int getSystolic() {
		return systolic;
	}

	public void setSystolic(int systolic) {
		this.systolic = systolic;
	}

	public int getDiastolic() {
		return diastolic;
	}

	public void setDiastolic(int diastolic) {
		this.diastolic = diastolic;
	}
	
	public String getBloodPressure() {
	    return systolic + "/" + diastolic;
	}
}
