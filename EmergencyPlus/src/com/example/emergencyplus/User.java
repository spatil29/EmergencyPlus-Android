package com.example.emergencyplus;

public class User {

	//Columns in t_user table
	private int id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emergencyNumber;

	//Empty constructor
	public User() {
		super();
	}

	//Constructor with fields
	public User(int id, String firstName, String lastName, String phoneNumber,
			String emergencyNumber) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emergencyNumber = emergencyNumber;
	}

	//Constructor with fields
	public User(String firstName, String lastName, String phoneNumber,
			String emergencyNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emergencyNumber = emergencyNumber;
	}
	
	//Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmergencyNumber() {
		return emergencyNumber;
	}

	public void setEmergencyNumber(String emergencyNumber) {
		this.emergencyNumber = emergencyNumber;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", phoneNumber=" + phoneNumber
				+ ", emergencyNumber=" + emergencyNumber + "]";
	}
	
	
}
