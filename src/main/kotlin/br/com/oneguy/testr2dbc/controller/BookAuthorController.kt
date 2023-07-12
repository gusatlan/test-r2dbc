package br.com.oneguy.testr2dbc.controller

import br.com.oneguy.testr2dbc.mapper.transform
import br.com.oneguy.testr2dbc.model.dto.BookAuthorDTO
import br.com.oneguy.testr2dbc.model.persist.BookAuthor
import br.com.oneguy.testr2dbc.service.BookAuthorService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

@RestController
class BookAuthorController(
    private val bookAuthorService: BookAuthorService
) {

    @GetMapping("/book_author")
    fun find(
        @RequestParam(value = "id", required = false) id: Long? = null,
        @RequestParam(value = "bookId", required = false) bookId: Long? = null,
        @RequestParam(value = "authorId", required = false) authorId: Long? = null
    ): Flux<BookAuthorDTO> {
        val items = if (id != null) {
            bookAuthorService.findById(id).toFlux()
        } else if (bookId != null && authorId != null) {
            bookAuthorService.findByAuthorAndBook(authorId, bookId)
        } else if (bookId != null) {
            bookAuthorService.findByBook(bookId)
        } else if (authorId != null) {
            bookAuthorService.findByAuthor(authorId)
        } else {
            bookAuthorService.findAll()
        }

        return items.map(BookAuthor::transform)
    }

    @PostMapping("/book_author")
    @PutMapping("/book_author")
    fun update(@RequestBody value: BookAuthorDTO): Flux<BookAuthorDTO> =
        bookAuthorService.save(value.transform()).map(BookAuthor::transform).flux()

    @DeleteMapping("/book_author/{id}")
    fun delete(@PathVariable("id", required = true) id: Long) = bookAuthorService.remove(id)

}