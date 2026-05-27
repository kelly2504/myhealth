package model;

public class User {
	private String username;
	private String firstname; //added user's first name
	private String lastname; //added user's last name
	private String password;
	private RecordList records; //added a list of records

	public User() {
	}
	
	public User(String username, String firstname, String lastname, String password) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		
		//set up list of records for the user 
		records = new RecordList();
		
	}
	
//	public User(String username, String password) {
//		this.username = username;
//		this.password = password;
//	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public RecordList getRecords() {
		return records;
	}

	public void setRecords(RecordList new_records) {
		records = new RecordList();
		for (int i = 0; i < new_records.getLength(); i++) {
			System.out.println(new_records.getRecordAtIndex(i).getRecord_number());
			records.addRecord(new_records.getRecordAtIndex(i));
		}
	}
}
