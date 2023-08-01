package com.cst348.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cst348.library.domain.Book;
import com.cst348.library.domain.BookRepository;
import com.cst348.library.domain.Patron;
import com.cst348.library.domain.PatronRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/library")
public class LibraryController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private PatronRepository patronRepository;

	@GetMapping("/book")
	public List<Book> getAllbook() {
		Iterable<Book> bookIterable = bookRepository.findAll();
		return StreamSupport.stream(bookIterable.spliterator(), false).collect(Collectors.toList());
	}

	@GetMapping("/book/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		return book.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/book")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		Book savedBook = bookRepository.save(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
	}

	@PutMapping("/book/{bookId}")
	public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book updatedBook) {
		Optional<Book> book = bookRepository.findById(bookId);
		if (book.isPresent()) {
			updatedBook.setBookId(bookId);
			Book savedBook = bookRepository.save(updatedBook);
			return ResponseEntity.ok(savedBook);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/book/{bookId}/return")
	public ResponseEntity<?> returnBook(@PathVariable Long bookId) {
		Optional<Book> bookOptional = bookRepository.findById(bookId);

		if (bookOptional.isPresent()) {
			Book book = bookOptional.get();

			if (book.getCheckoutPatron() == null) {
				// Book is not checked out, cannot be returned
				String errorMessage = "Book is not checked out, cannot be returned.";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
			}

			// Update book information to mark it as returned
			book.setCheckoutPatron(null);
			book.setCheckoutDate(null);
			bookRepository.save(book);

			return ResponseEntity.ok("Book has been returned successfully.");
		} else {
			String errorMessage = "Invalid bookId.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
	}

	@DeleteMapping("/book/{bookId}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		if (book.isPresent()) {
			bookRepository.deleteById(bookId);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/book/{bookId}/checkout/{patronId}")
	public ResponseEntity<?> checkoutBook(@PathVariable Long bookId, @PathVariable Long patronId) {
		// First, check if the book exists in the database
		Optional<Book> bookOptional = bookRepository.findById(bookId);
		if (!bookOptional.isPresent()) {
			String errorMessage = "bookId or patronId invalid";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}

		// Next, check if the patron exists in the database
		Optional<Patron> patronOptional = patronRepository.findById(patronId);
		if (!patronOptional.isPresent()) {
			String errorMessage = "bookId or patronId invalid";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}

		// If both the book and patron exist, proceed with the checkout process
		Book book = bookOptional.get();
		Patron patron = patronOptional.get();

		if (book.getCheckoutPatron() != null) {
			String errorMessage = "Book with ID: " + bookId + " is already checked out.";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}

		// Set the checkout patron and checkout date for the book
		book.setCheckoutPatron(patron);
		book.setCheckoutDate(LocalDate.now());
		bookRepository.save(book);

		// Optionally, you can return the updated book object in the response
		return ResponseEntity.ok(book);
	}

	@GetMapping("/patron")
	public List<Patron> getAllpatron() {
		Iterable<Patron> patronIterable = patronRepository.findAll();
		return StreamSupport.stream(patronIterable.spliterator(), false).collect(Collectors.toList());
	}

	@GetMapping("/patron/{patronId}")
	public ResponseEntity<?> getPatronById(@PathVariable Long patronId) {
		Optional<Patron> patronOptional = patronRepository.findById(patronId);

		if (patronOptional.isPresent()) {
			Patron patron = patronOptional.get();
			List<Book> checkedOutBooks = patron.getCheckedOutBooks();

			// If the patron has checked out books
			if (checkedOutBooks != null && !checkedOutBooks.isEmpty()) {
				return ResponseEntity.ok(checkedOutBooks);
			} else {
				// If the patron has no checked out books
				String message = "No books have been checked out by the patron.";
				return ResponseEntity.ok(Collections.singletonMap("message", message));
			}
		} else {
			String errorMessage = "patronId invalid";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
	}

	@PostMapping("/patron/{firstName}/{lastName}")
	public ResponseEntity<?> createPatron(@PathVariable String firstName, @PathVariable String lastName) {
		// Check if the provided names are valid (not empty or null)
		if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
			String errorMessage = "Invalid name";
			return ResponseEntity.badRequest().body(errorMessage);
		}

		// Generate a random 9-digit patron ID that is not already in use
		Random random = new Random();
		long generatedPatronId;
		boolean isIdInUse;
		do {
			generatedPatronId = 100_000_000L + random.nextInt(900_000_000);
			isIdInUse = patronRepository.existsById(generatedPatronId);
		} while (isIdInUse);

		// Create the new patron object
		Patron newPatron = new Patron(firstName + " " + lastName);
		newPatron.setPatronId(generatedPatronId);

		// Save the new patron to the database
		patronRepository.save(newPatron);

		return ResponseEntity.ok(newPatron);
	}

	@PutMapping("/patron/{patronId}")
	public ResponseEntity<Patron> updatePatron(@PathVariable Long patronId, @RequestBody Patron updatedPatron) {
		Optional<Patron> patron = patronRepository.findById(patronId);
		if (patron.isPresent()) {
			updatedPatron.setPatronId(patronId);
			Patron savedPatron = patronRepository.save(updatedPatron);
			return ResponseEntity.ok(savedPatron);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/patron/{patronId}")
	public ResponseEntity<Void> deletePatron(@PathVariable Long patronId) {
		Optional<Patron> patron = patronRepository.findById(patronId);
		if (patron.isPresent()) {
			patronRepository.deleteById(patronId);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
