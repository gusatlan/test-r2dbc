package br.com.oneguy.testr2dbc.service

import br.com.oneguy.testr2dbc.mapper.transform
import br.com.oneguy.testr2dbc.model.dto.AuthorDTO
import br.com.oneguy.testr2dbc.model.dto.BookDTO
import br.com.oneguy.testr2dbc.model.persist.Author
import br.com.oneguy.testr2dbc.model.persist.Book
import br.com.oneguy.testr2dbc.model.persist.BookAuthor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BookAuthorFacade(
    private val authorService: AuthorService,
    private val bookService: BookService,
    private val bookAuthorService: BookAuthorService
) {

    private fun findBook(bookId: Long? = null, title: String? = null, authorName: String? = null): Flux<Book> {
        val items = if (bookId != null) {
            bookService.findById(bookId).flux()
        } else if (title != null) {
            bookService.findByTitle(title)
        } else if (authorName != null) {
            findBookByAuthorName(authorName)
        } else {
            bookService.findAll()
        }

        return items.flatMap(this::apply)
    }

    private fun findBookByAuthorName(authorName: String): Flux<Book> {
        return authorService
            .findByName(authorName)
            .map(Author::id)
            .flatMap { bookAuthorService.findByAuthor(it!!) }
            .map(BookAuthor::bookId)
            .flatMap { bookService.findById(it!!) }
    }

    private fun findAuthor(authorId: Long? = null, authorName: String? = null): Flux<Author> {
        val items = if (authorId != null) {
            authorService.findById(authorId).flux()
        } else if (authorName != null) {
            authorService.findByName(authorName)
        } else {
            authorService.findAll()
        }

        return items.flatMap(this::apply)
    }

    private fun apply(value: Book): Mono<Book> {
        return bookAuthorService
            .findByBook(value.id!!)
            .flatMap { authorService.findById(it.authorId) }
            .collectList()
            .map {
                value.authors = it
                value
            }
    }

    private fun apply(value: Author): Mono<Author> {
        return bookAuthorService
            .findByAuthor(value.id!!)
            .flatMap { bookService.findById(it.bookId) }
            .collectList()
            .map {
                value.books = it
                value
            }
    }

    fun findBookDTO(bookId: Long? = null, title: String? = null, authorName: String? = null): Flux<BookDTO> {
        return findBook(
            bookId = bookId,
            title = title,
            authorName = authorName
        ).map(Book::transform)
    }

    fun findAuthorDTO(authorId: Long? = null, authorName: String? = null): Flux<AuthorDTO> {
        return findAuthor(
            authorId = authorId,
            authorName = authorName
        ).map(Author::transform)
    }

    @Transactional
    fun save(value: AuthorDTO): Mono<AuthorDTO> = authorService.save(value.transform()).map(Author::transform)

    @Transactional
    fun save(value: BookDTO): Mono<BookDTO> = bookService.save(value.transform()).map(Book::transform)

    @Transactional
    fun removeBook(id: Long): Mono<Void> {
        bookAuthorService
            .findByBook(id)
            .map(BookAuthor::id)
            .flatMap {
                bookAuthorService.remove(it!!)
            }
            .subscribe()

        return bookService.remove(id)
    }

    @Transactional
    fun removeAuthor(id: Long): Mono<Void> {
        bookAuthorService
            .findByAuthor(id)
            .map(BookAuthor::id)
            .flatMap {
                bookAuthorService.remove(it!!)
            }
            .subscribe()

        return authorService.remove(id)
    }

    @Transactional
    fun stress(quantity: Long=1000L) {

    }
}