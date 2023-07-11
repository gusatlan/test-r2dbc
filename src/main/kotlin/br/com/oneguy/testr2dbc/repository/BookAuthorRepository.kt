package br.com.oneguy.testr2dbc.repository

import br.com.oneguy.testr2dbc.model.persist.BookAuthor
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface BookAuthorRepository : ReactiveCrudRepository<BookAuthor, Long> {

    fun findByAuthorId(authorId: Long): Flux<BookAuthor>

    fun findByBookId(bookId: Long): Flux<BookAuthor>

    fun findByAuthorIdAndBookId(authorId: Long, bookId: Long) : Flux<BookAuthor>
}