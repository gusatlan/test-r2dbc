package br.com.oneguy.testr2dbc.repository

import br.com.oneguy.testr2dbc.model.persist.Book
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import java.time.LocalDate

interface BookRepository: ReactiveCrudRepository<Book, Long> {

    fun findByTitleIgnoreCase(title: String) : Flux<Book>
    fun findByPublishDateBetween(begin: LocalDate, end: LocalDate): Flux<Book>

    @Query("select o from Book o fetch join o.authors a where a.name like :authorName")
    fun findByAuthorIgnoreCase(authorName: String) : Flux<Book>

}