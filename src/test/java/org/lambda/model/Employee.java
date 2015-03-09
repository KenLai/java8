package org.lambda.model;

public class Employee {

	private String firstName;
	private String lastName;
	private int personnelNumber;
	private int yearsOfExperience;
	private Title title;

        private int callCount;
        
	public static int compareByFirstName(Employee e1, Employee e2) {
		return e1.getFirstName().compareTo(e2.getFirstName());
	}

	public int compareByLastName(Employee e1, Employee e2) {
                callCount++;
		return e1.getLastName().compareTo(e2.getLastName());
	}
	
	public enum Title {
		ENGINEER, SENIOR_ENGINEER, STAFF_ENGINEER, DIRECTOR
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title level) {
		this.title = level;
	}

	public Employee() {
	}

	public Employee(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getPersonnelNumber() {
		return personnelNumber;
	}

	public void setPersonnelNumber(int personnelNumber) {
		this.personnelNumber = personnelNumber;
	}

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	@Override
	public String toString() {
		return "firstName:" + firstName + " lastName:" + lastName
				+ " personnelNumber:" + this.personnelNumber + " title:"
				+ this.title + " yearsOfExperience:" + this.yearsOfExperience + " callCount:"+callCount;
	}
}
