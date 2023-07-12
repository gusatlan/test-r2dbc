package br.com.oneguy.testr2dbc.controller

import br.com.oneguy.testr2dbc.service.BookAuthorFacade
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class StressController(
    private val service: BookAuthorFacade
) {

    @GetMapping("/stress/{quantity}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun stress(
        @PathVariable("quantity", required = true) quantity: Long
    ) = service.stress(quantity)
}