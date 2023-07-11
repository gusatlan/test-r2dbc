package br.com.oneguy.testr2dbc.repository

import br.com.oneguy.testr2dbc.model.persist.Author
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface AuthorRepository: ReactiveCrudRepository<Author, Long> {

    fun findByName(name: String): Flux<Author>

}