package com.cst348.library.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "books") // Specify the table name

public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String author;

	@ManyToOne
	@JoinColumn(name = "checkout_patron_id")
	private Patron checkoutPatron;

	@Column
	private LocalDate checkoutDate;

	// Constructors
	public Book() {
	}

	public Book(String title, String author) {
		this.title = title;
		this.author = author;
	}

	// Getters and Setters
	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Patron getCheckoutPatron() {
		return checkoutPatron;
	}

	public void setCheckoutPatron(Patron checkoutPatron) {
		this.checkoutPatron = checkoutPatron;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	// toString method
	@Override
	public String toString() {
		return "Book{" + "bookId=" + bookId + ", title='" + title + '\'' + ", author='" + author + '\''
				+ ", checkoutPatron=" + checkoutPatron + ", checkoutDate=" + checkoutDate + '}';
	}
}
