package br.com.oneguy.testr2dbc.service

import br.com.oneguy.testr2dbc.model.persist.Author
import br.com.oneguy.testr2dbc.repository.AuthorRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AuthorService(
    private val authorRepository: AuthorRepository,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun findAll(): Flux<Author> = authorRepository
        .findAll()
        .doOnNext { logger.debug("AuthorRepository:findAll => $it") }

    fun findById(id: Long): Mono<Author> {
        return authorRepository
            .findById(id)
            .doOnNext { logger.debug("AuthorService:findById $id => $it") }
            .doOnError { logger.error("AuthorService:findById $id => $it") }
    }

    fun findByName(name: String): Flux<Author> {
        return authorRepository
            .findByName(name.trim().uppercase())
            .doOnNext { logger.debug("AuthorService:findByName $name => $it") }
            .doOnError { logger.error("AuthorService:findByName $name => $it") }
    }

    @Transactional
    fun save(value: Author): Mono<Author> {
        return save(listOf(value)).last()
    }

    @Transactional
    fun save(values: Collection<Author>): Flux<Author> {
        return authorRepository
            .saveAll(values)
            .doOnNext {
                logger.debug("AuthorService:save $it")
            }
            .doOnError {
                logger.error("AuthorService:save $it")
            }
    }

    @Transactional
    fun remove(id: Long): Mono<Void> {
        return findById(id)
            .flatMap {
                authorRepository.delete(it).subscribe()
                Mono.just(it)
            }
            .doOnNext {
                logger.debug("AuthorService:remove $id => $it")
            }
            .doOnError {
                logger.debug("AuthorService:remove $id => $it")
            }
            .then()
    }
}