package br.com.oneguy.testr2dbc.controller

import br.com.oneguy.testr2dbc.model.dto.BookDTO
import br.com.oneguy.testr2dbc.service.BookAuthorFacade
import org.springframework.web.bind.annotation.*

@RestController
class BookController(
    private val service: BookAuthorFacade
) {

    @GetMapping("/books")
    fun find(
        @RequestParam(value = "id", required = false) id: Long? = null,
        @RequestParam("title", required = false) title: String? = null,
        @RequestParam("author", required = false) authorName: String? = null
    ) = service.findBookDTO(bookId = id, title = title, authorName = authorName)

    @PostMapping("/books")
    @PutMapping("/books")
    fun update(@RequestBody value: BookDTO) = service.save(value)

    @DeleteMapping("/books/{id}")
    fun delete(@PathVariable("id", required = true) id: Long) = service.removeBook(id)


}