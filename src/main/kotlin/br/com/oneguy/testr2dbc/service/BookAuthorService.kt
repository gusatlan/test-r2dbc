package br.com.oneguy.testr2dbc.service

import br.com.oneguy.testr2dbc.model.persist.BookAuthor
import br.com.oneguy.testr2dbc.repository.BookAuthorRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BookAuthorService(
    private val bookAuthorRepository: BookAuthorRepository
) {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun findAll(): Flux<BookAuthor> = bookAuthorRepository
        .findAll()
        .doOnNext { logger.debug("BookAuthorService:findAll => $it") }

    fun findById(id: Long): Mono<BookAuthor> {
        return bookAuthorRepository
            .findById(id)
            .doOnNext { logger.debug("BookAuthorService:findById $id => $it") }
            .doOnError { logger.error("BookAuthorService:findById $id => $it") }
    }

    fun findByAuthor(authorId: Long): Flux<BookAuthor> {
        return bookAuthorRepository
            .findByAuthorId(authorId)
            .doOnNext { logger.debug("BookAuthorService:findByAuthor $authorId => $it") }
            .doOnError { logger.error("BookAuthorService:findByAuthor $authorId => $it") }
    }

    fun findByBook(bookId: Long): Flux<BookAuthor> {
        return bookAuthorRepository
            .findByBookId(bookId)
            .doOnNext { logger.debug("BookAuthorService:findByBook $bookId => $it") }
            .doOnError { logger.error("BookAuthorService:findByBook $bookId => $it") }
    }

    fun findByAuthorAndBook(authorId: Long, bookId: Long): Flux<BookAuthor> {
        return bookAuthorRepository
            .findByAuthorIdAndBookId(authorId, bookId)
            .doOnNext { logger.debug("BookAuthorService:findByAuthorAndBook $bookId => $it") }
            .doOnError { logger.error("BookAuthorService:findByAuthorAndBook $bookId => $it") }
    }

    @Transactional
    fun save(value: BookAuthor): Mono<BookAuthor> {
        return save(listOf(value)).last()
    }

    @Transactional
    fun save(values: Collection<BookAuthor>): Flux<BookAuthor> {
        return bookAuthorRepository
            .saveAll(values)
            .doOnNext {
                logger.debug("BookAuthorService:save $it")
            }
            .doOnError {
                logger.error("BookAuthorService:save $it")
            }
    }

    @Transactional
    fun remove(id: Long): Mono<Void> {
        return findById(id)
            .flatMap {
                bookAuthorRepository.delete(it).subscribe()
                Mono.just(it)
            }
            .doOnNext {
                logger.debug("BookAuthorService:remove $id => $it")
            }
            .doOnError {
                logger.debug("BookAuthorService:remove $id => $it")
            }.then()
    }
}