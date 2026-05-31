package Utils;

public class RecordValuesChecker {
	
	public RecordValuesChecker() {}
	
	public static final double MIN_WEIGHT = 0.0;
	public static final double MAX_WEIGHT = 600.0;
	public static final double MIN_TEMP = 0.0;
	public static final double MAX_TEMP = 45.0;
	public static final int MIN_SYSTOLIC = 0;
	public static final int MAX_SYSTOLIC = 400;
	public static final int MIN_DIASTOLIC = 0;
	public static final int MAX_DIASTOLIC = 300;

	public String checkRecords(double weight, double temperature, int systolic, int diastolic, String note) {
		String message = "Good";
		if (weight <= MIN_WEIGHT || weight > MAX_WEIGHT ) {
			return "Weight out of range.";
		}
		
		if (temperature < MIN_TEMP || temperature > MAX_TEMP) {
			return "Temperature out of range.";
		}
		
		if (systolic < MIN_SYSTOLIC || systolic > MAX_SYSTOLIC) {
			return "Systolic value out of range.";
		}
		
		if (diastolic < MIN_DIASTOLIC || diastolic > MAX_DIASTOLIC) {
			return "Diastolic value out of range";
		}
		
		String[] words = note.trim().split(" ");
		if (words.length > 50) {
			return "Note should be not be more than 50 words.";
		}	
		
		return message;
	}
}
