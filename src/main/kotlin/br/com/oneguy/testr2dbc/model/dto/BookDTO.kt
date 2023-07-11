package br.com.oneguy.testr2dbc.model.dto

import java.time.LocalDate

class BookDTO(
    val id: Long? = null,
    title: String = "",
    val publishDate: LocalDate = LocalDate.now(),
    val authors: Collection<AuthorDTO> = emptySet(),
    val edition: Int = 1
) {
    val title = title.trim()

    override fun equals(other: Any?) = other != null && other is BookDTO && id == other.id
    override fun hashCode() = id.hashCode()
    override fun toString() = """{"id": "$id", "title": "$title"}"""
}