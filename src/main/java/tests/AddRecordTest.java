package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Utils.RecordValuesChecker;
//import controller.AddRecordController;


public class AddRecordTest {
	RecordValuesChecker valuesChecker;
	@Before
	public void setup() {
		valuesChecker = new RecordValuesChecker();
	}
	
	
	//add record successfully
	@Test
	public void TestRecordPass() {
		assertEquals("Good", valuesChecker.checkRecords(60.0, 35.0, 120, 80, "This is a healthy person"));
	}
	
	@Test
	public void WeightOutOfRangeTest() {
		assertEquals("Weight out of range.", valuesChecker.checkRecords(-20.0, 35.0, 120, 80, "This is not a good record"));
	}
	
	@Test 
	public void TemperatureOutOfRange() {
		assertEquals("Temperature out of range.", valuesChecker.checkRecords(36.0, 50.0, 120, 80, "This is not a good record"));
	}
	
	@Test
	public void BloodPressureOutOfRange() {
		
		assertEquals("Systolic value out of range.", valuesChecker.checkRecords(70.0, 36.0, 600, 100, "This is not a good record"));
	}
	
	@Test
	public void veryLongNoteTest() {
		String note = "";
		for (int i = 0; i < 60; ++i) {
			note += "a ";
		}
		assertEquals("Note should be not be more than 50 words.", valuesChecker.checkRecords(35.0, 36.5, 120, 80, note));
	}
	
}
