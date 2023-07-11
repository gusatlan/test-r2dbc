package br.com.oneguy.testr2dbc.mapper

import br.com.oneguy.testr2dbc.model.dto.AuthorDTO
import br.com.oneguy.testr2dbc.model.dto.BookAuthorDTO
import br.com.oneguy.testr2dbc.model.dto.BookDTO
import br.com.oneguy.testr2dbc.model.persist.Author
import br.com.oneguy.testr2dbc.model.persist.Book
import br.com.oneguy.testr2dbc.model.persist.BookAuthor

fun Book.transform(): BookDTO {
    val items = authors.stream().map(Author::transform).toList()

    return BookDTO(
        id = id,
        title = title,
        publishDate = publishDate,
        edition = edition,
        authors = items
    )
}

fun BookDTO.transform(): Book {
    val items = authors.stream().map(AuthorDTO::transform).toList()

    return Book(
        id = id,
        title = title,
        publishDate = publishDate,
        edition = edition
    ).apply {
        authors = items
    }
}

fun Author.transform(): AuthorDTO {
    val items = books.stream().map(Book::transform).toList()

    return AuthorDTO(
        id = id,
        name = name,
        books = items
    )
}

fun AuthorDTO.transform(): Author {
    val items = books.stream().map(BookDTO::transform).toList()

    return Author(
        id = id,
        name = name
    ).apply {
        books = items
    }
}

fun BookAuthor.transform(): BookAuthorDTO {
    return BookAuthorDTO(
        id = id,
        bookId = bookId,
        authorId = authorId
    )
}

fun BookAuthorDTO.transform(): BookAuthor {
    return BookAuthor(
        id = id,
        bookId = bookId,
        authorId = authorId
    )
}

