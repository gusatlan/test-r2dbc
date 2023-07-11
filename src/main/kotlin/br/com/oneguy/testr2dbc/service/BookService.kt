package br.com.oneguy.testr2dbc.service

import br.com.oneguy.testr2dbc.model.persist.Book
import br.com.oneguy.testr2dbc.repository.BookRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val bookAuthorService: BookAuthorService,
    private val authorService: AuthorService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun findAll(): Flux<Book> = bookRepository
        .findAll()
        .flatMap(this::apply)
        .doOnNext { logger.debug("BookRepository:findAll => $it") }


    fun findById(id: Long): Mono<Book> {
        return bookRepository
            .findById(id)
            .flatMap(this::apply)
            .doOnNext { logger.debug("BookService:findById $id => $it") }
            .doOnError { logger.error("BookService:findById $id => $it") }
    }

    fun findByAuthor(author: String): Flux<Book> {
        return bookRepository
            .findByAuthorIgnoreCase(author.trim().uppercase())
            .flatMap(this::apply)
            .doOnNext { logger.debug("BookService:findByAuthor $author => $it") }
            .doOnError { logger.error("BookService:findByAuthor $author => $it") }
    }


    fun findByTitle(title: String): Flux<Book> {
        return bookRepository
            .findByTitleIgnoreCase(title.trim().uppercase())
            .flatMap(this::apply)
            .doOnNext { logger.debug("BookService:findByTitle $title => $it") }
            .doOnError { logger.error("BookService:findByTitle $title => $it") }
    }

    fun apply(value: Book) : Mono<Book> {
        return bookAuthorService
            .findByBook(value.id!!)
            .flatMap { authorService.findById(it.authorId) }
            .collectList()
            .map {
                value.authors = it
                value
            }
    }

    fun save(value: Book): Flux<Book> = save(listOf(value))

    fun save(values: Collection<Book>): Flux<Book> {
        return bookRepository
            .saveAll(values)
            .doOnNext { logger.debug("BookService:save $it") }
            .doOnError { logger.error("BookService:save $it") }
    }

    fun remove(id: Long): Mono<Book> {
        return findById(id)
            .flatMap {
                bookRepository.delete(it)
                Mono.just(it)
            }
            .doOnNext { logger.debug("BookService:remove $id => $it") }
            .doOnError { logger.debug("BookService:remove $id => $it") }
    }

}