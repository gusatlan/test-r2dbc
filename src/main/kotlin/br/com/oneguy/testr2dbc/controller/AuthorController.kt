package br.com.oneguy.testr2dbc.controller

import br.com.oneguy.testr2dbc.mapper.transform
import br.com.oneguy.testr2dbc.model.dto.AuthorDTO
import br.com.oneguy.testr2dbc.model.persist.Author
import br.com.oneguy.testr2dbc.service.AuthorService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class AuthorController(
    private val authorService: AuthorService
) {

    @GetMapping("/authors")
    fun find(@RequestParam(value = "id", required = false) id: Long? = null): Flux<AuthorDTO> {
        return if (id != null) {
            authorService.findById(id).flux()
        } else {
            authorService.findAll()
        }.map(Author::transform)
    }

    @PostMapping("/authors")
    @PutMapping("/authors")
    fun update(@RequestBody value: AuthorDTO): Mono<AuthorDTO> =
        authorService.save(value.transform()).map(Author::transform)

    @DeleteMapping("/authors/{id}")
    fun delete(@PathVariable("id", required = true) id: Long): Mono<AuthorDTO> =
        authorService.remove(id).map(Author::transform)


}