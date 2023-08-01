package com.cst348.library.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "patrons") // Specify the table name
public class Patron {

	@Id
	private Long patronId;

	@Column(nullable = false)
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "checkoutPatron")
	private List<Book> checkedOutBooks;

	// Constructors
	public Patron() {
	}

	public Patron(String name) {
		this.name = name;
	}

	// Getters and Setters
	public Long getPatronId() {
		return patronId;
	}

	public void setPatronId(Long patronId) {
		this.patronId = patronId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getCheckedOutBooks() {
		return checkedOutBooks;
	}

	public void setCheckedOutBooks(List<Book> checkedOutBooks) {
		this.checkedOutBooks = checkedOutBooks;
	}

	// toString method
	@Override
	public String toString() {
		return "Patron{" + "patronId=" + patronId + ", name='" + name + '\'' + '}';
	}
}
