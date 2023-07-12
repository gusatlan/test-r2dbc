package br.com.oneguy.testr2dbc.controller

import br.com.oneguy.testr2dbc.model.dto.AuthorDTO
import br.com.oneguy.testr2dbc.service.BookAuthorFacade
import org.springframework.web.bind.annotation.*

@RestController
class AuthorController(
    private val service: BookAuthorFacade
) {

    @GetMapping("/authors")
    fun find(
        @RequestParam(value = "id", required = false) authorId: Long? = null,
        @RequestParam(value = "name", required = false) authorName: String? = null
    ) = service.findAuthorDTO(authorId = authorId, authorName = authorName)

    @PostMapping("/authors")
    @PutMapping("/authors")
    fun update(@RequestBody value: AuthorDTO) = service.save(value)

    @DeleteMapping("/authors/{id}")
    fun delete(@PathVariable("id", required = true) id: Long) = service.removeAuthor(id)


}