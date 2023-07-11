CREATE TABLE author(
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	version INT
);


CREATE TABLE book(
	id SERIAL PRIMARY KEY,
	title VARCHAR(255) NOT NULL,
	publish_date DATE NOT NULL,
	edition INT DEFAULT 1
);

CREATE TABLE book_author(
	id SERIAL PRIMARY KEY,
	book INT NOT NULL,
	author INT NOT NULL
);

CREATE UNIQUE INDEX unx_book_author ON book_author(book,author);

