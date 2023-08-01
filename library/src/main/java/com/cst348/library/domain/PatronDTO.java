package com.cst348.library.domain;

public class PatronDTO {

	private Long patronId;
	private String firstName;
	private String lastName;

	// Constructors
	public PatronDTO() {
	}

	public PatronDTO(Long patronId, String firstName, String lastName) {
		this.patronId = patronId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	// Getters and Setters
	public Long getPatronId() {
		return patronId;
	}

	public void setPatronId(Long patronId) {
		this.patronId = patronId;
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
}
