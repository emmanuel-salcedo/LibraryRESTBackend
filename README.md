# Library Management System

This project is a Library Management System that allows users to manage books and patrons in a library. It provides RESTful APIs for performing CRUD operations on books and patrons, as well as checking out and returning books.

## Table of Contents

- [Description](#description)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)

## Description

The Library Management System is a web application built using Spring Boot, which provides a backend API to manage books and patrons in a library. It allows users to:

- Retrieve a list of all books in the library.
- Get details of a specific book by its ID.
- Add new books to the library.
- Update existing book information.
- Delete books from the library.
- Check out a book to a patron.
- Return a checked-out book to the library.
- Retrieve a list of all patrons.
- Get details of a specific patron by their ID.
- Add new patrons to the library.
- Update existing patron information.
- Delete patrons from the library.

## Installation

1. Clone this repository to your local machine using the following command:

```bash
git clone https://github.com/your-username/library-management-system.git
```

2. Create a MySQL database and update the database configuration in the `application.properties` file located in the `src/main/resources` directory.

3. Build the project using Maven:

```bash
cd library-management-system
mvn clean package
```

4. Run the application:

```bash
java -jar target/library-management-system.jar
```

The application will start running on `http://localhost:8080`.

## Usage

To use the Library Management System, you can interact with it using any REST API client like Postman or cURL. The application provides various API endpoints to perform operations on books and patrons.

## API Endpoints

The following are the main API endpoints available in the application:

- **GET /library/book**: Get a list of all books in the library.
- **GET /library/book/{bookId}**: Get details of a specific book by its ID.
- **POST /library/book**: Add a new book to the library.
- **PUT /library/book/{bookId}**: Update information of an existing book.
- **PUT /library/book/{bookId}/return**: Return a checked-out book to the library.
- **DELETE /library/book/{bookId}**: Delete a book from the library.
- **PUT /library/book/{bookId}/checkout/{patronId}**: Check out a book to a patron.
- **GET /library/patron**: Get a list of all patrons in the library.
- **GET /library/patron/{patronId}**: Get details of a specific patron by their ID.
- **POST /library/patron/{firstName}/{lastName}**: Add a new patron to the library.
- **PUT /library/patron/{patronId}**: Update information of an existing patron.
- **DELETE /library/patron/{patronId}**: Delete a patron from the library.

Make sure to provide the appropriate request data and HTTP methods while interacting with these endpoints.
