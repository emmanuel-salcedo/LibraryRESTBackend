create database library;

create table patrons (
patron_id bigint primary key,
name varchar(250) not null );

create table books (
book_id bigint primary key,
title varchar(250) not null,
author varchar(250) not null,
checkout_patron_id bigint,
checkout_date date,
foreign key (checkout_patron_id) references patrons(patron_id));

INSERT INTO library.patron (patron_id, name)
VALUES (425198003, 'Ryan Reynolds');

-- this book is checked out
INSERT INTO library.book (book_id, title, author, checkout_patron_id, checkout_date)
VALUES (981003724, 'Book Title 1', 'Author 1', 425198003, '2023-07-31');
-- this book has not been checkedout
INSERT INTO library.book (book_id, title, author, checkout_patron_id, checkout_date)
VALUES (981456123, 'Book Title 2', 'Author 2', NULL, NULL);
