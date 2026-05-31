
# MyHealth

MyHealth is a GUI made with JavaFX and FXML that allows users
to keep track of their personal health records by recording their
weight, temperature, blood pressure and clinical notes.
It follows the Model-View-Controller design framework. 

---
## Features

- Secure login and sign using BCrypt password hashing. 
- Create, view and update personal health records. 
- Auto-incremented record numbers in the format `RN-0001`.
- Download single or multiple records as `.txt` file.
- User record isolation - each user can only see their own records.
- Reusable UI components (ex: Menu bar) - using FXML files

---
## Components 

`\main`
- Main.java - main entry point - sets new scene and calls the stage to the LoginController

`\controller` - backend logic
- AddRecordController.java - Operates the logic `/view/AddRecordView.fxml` - calls the RecordDao to 
create a new record and uses the `/utils/RecordValuesChecker` to check if the values are in within the right
range. 

- ChangePasswordController.java - Operates the logic `/view/ChangePasswordView.fxml` - calls the UserDao to 
change and hash the new password and checks if the password is in the correct format using the `/utils/PasswordFormatChecker.java`

- DeleteConfirmationController.java - Operates the logic for `/view/DeleteConfirmationView.fxml`.

- HomeController.java - Operates the logic for `/view/DashboardView.fxml` - calls the stage to the RecordTableController.
It allows the user to view a brief description of their records. They can then open each record to view their full details using 
the RecordController. Users can delete records they do not want anymore - this calls the DeleteConfirmationController. 
Users can also download single or multiple records which calls the `/utils/FileManager.java` utility class. 

- LoginController.java - Operates the logic for `/view/LoginUpdatedView.fxml` - Calls the UserDao to retrieve user 
data and check if their username and password match the records in the database. Has a button to allow user sign up
which calls the SignupController.

- MenuBarController.java - Operates the logic for `/view/MenuBar.fxml` - is called as a child node since
it is used by other pages to navigate through the application. Contains the button that gracefully exits the application.
Allows user to navigate to the `ProfileView` page and `ProfileUpdate` page. 

- ProfileController.java - Operates the logic for `/view/ProfileView.fxml`. - Calls the ChangePasswordController that 
allows users to change their password if desired. 

- RecordController.java - Operates the logic for `/view/RecordView.fxml` that allows users to view a specific 
user record, calls the RecordDao to create new records. Also calls the RecordUpdateController to edit existing records 
and the `/utils/FileManager/java` to download single records.

- RecordTableController.java - Operates the logic for the `/view/RecordTableView.fxml` that reads record values from the
RecordList model which stores values from the RecordDao for each respective user. 

- RecordUpdateController.java - Operates the logic for the `/view/RecordUpdateView.fxml` that allows users to
update record details. Calls the RecordDao to update the values for the specific record and the values are
checked using the `/utils/RecordValuesChecker.java`

- SignupController.java - Operates the logic for `/view/SignUpUpdatedView.fxml` - Calls the UserDao to create new user and 
hashes the password using the `/utils/PasswordManager.java`. After creating an account, users can go back to the login page
and use their credentials to log into the application. 

- UpdateProfileController.java - Operates the logic for `/view/UpdateProfileView.fxml` - Calls the UserDao to 
update the values of the user's first name and/or last name. 


`\dao` - Data Access Object - links controllers to the database. 
- Database.java - creates the connection to the database
- RecordDao.java - Interface that defines the implementation of the database access
for health records.
- RecordDaoImpl.java - implements the RecordDao Interface.
- UserDao.java - Interface that defines the implementation of the database access for 
user records.
- UserDaoImpl.java - implements the UserDao Interface.

`\model`
- HealthRecord.java - defines a health record
- Model.java - sets up the databases, and stores the value of the current user
- RecordList.java - defines an Observable list for each user that manages each user's health records
- User.java - defines a user - each user has a username, firstname, lastname and password.

`\tests`
- AddRecordTest.java - tests to check health record values 
- PasswordFormatTest.java - tests format of passwords
- UserTest.java- tests user signup: user uniqueness and password encryption

`\utils` - utility classes used throughout the MVC
- FileManager.java - creates a file that stores health record(s).
- PasswordFormatChecker.java - checkes that a password is in the right format
- PasswordManager.java - handles password encryption and verification using BCrypt
- RecordNumberGenerator.java - Singleton that enables unique record numbers to be generated 
through different users. 
- RecordValuesChecker.java - checks if the weight, temperature and blood pressure values are in the 
right format.

`\resources\view` - FXML frontend
- AddRecordView.fxml - Form that reads in record values and allows new records to be created. 
- ChangePasswordView.fxml - Form that allows users to change their password.
- DashboardView.fxml - Main page of the GUI
- DeleteConfirmationView.fxml - Asks the user for confirmation after they try deleting a record
- LoginUpdatedView.fxml - Login page - First page every user sees when starting the GUI
- MenuBar.fxml - MenuBar included in the Dashboard view and profile view. 
- ProfileView.fxml - Shows the profile details of the user and allows user to change their password
- RecordTableView.fxml - Table of records - included by the dash board.
- RecordUpdateView.fxml - Form that allows user to edit an existing record. 
- RecordView.fxml - Shows details of a specific record to the user.
- SignUpUpdatedView.fxml - Form that allows user to create a new account
- UpdateProfileView.fxml - Form that allows user to change their first name and/or last name.

---
## Library Stack
This program was mainly developed in Java but also uses the following:

* FXML Building: Scene Builder (external application)
* GUI Framework: JavaFX
* Database Library: JDBC and Sqlite
* Password Hashing: BCrypt (jbcrypt)
* Testing Library: JUnit4

---
This program was developed by:

Name: Kelly Wan Wing Kai

Student Number: s4109555

Solution to RMIT's COSC2391, Further Programming assignment 2. 

-------------------------------------------------------------------------
------------------------------------------------------------------------
