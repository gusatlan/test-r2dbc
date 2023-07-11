package br.com.oneguy.testr2dbc.controller

import br.com.oneguy.testr2dbc.mapper.transform
import br.com.oneguy.testr2dbc.model.dto.BookDTO
import br.com.oneguy.testr2dbc.model.persist.Book
import br.com.oneguy.testr2dbc.service.BookService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class BookController(
    private val bookService: BookService
) {

    @GetMapping("/books")
    fun find(
        @RequestParam(value = "id", required = false) id: Long? = null,
        @RequestParam("title", required = false) title: String? = null,
        @RequestParam("author", required = false) author: String? = null
    ): Flux<BookDTO> {
        val books = if (id != null) {
            bookService.findById(id).flux()
        } else if (title != null) {
            bookService.findByTitle(title)
        } else if (author != null) {
            bookService.findByAuthor(author)
        } else {
            bookService.findAll()
        }

        return books.map(Book::transform)
    }

    @PostMapping("/books")
    @PutMapping("/books")
    fun update(@RequestBody value: BookDTO): Flux<BookDTO> = bookService.save(value.transform()).map(Book::transform)

    @DeleteMapping("/books/{id}")
    fun delete(@PathVariable("id", required = true) id: Long): Mono<BookDTO> =
        bookService.remove(id).map(Book::transform)


}