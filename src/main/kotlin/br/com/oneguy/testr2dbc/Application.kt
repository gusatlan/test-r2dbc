package br.com.oneguy.testr2dbc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
//TODO Criar Facade para associar books e authors, usá-lo nos controllers