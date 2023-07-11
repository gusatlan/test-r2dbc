package br.com.oneguy.testr2dbc.model.persist

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("book")
class Book(
    id: Long? = null,
    title: String = "",
    val publishDate: LocalDate = LocalDate.now(),
    val edition: Int = 1
) {
    var id = id
        @Id
        @Column("id")
        get

    val title = title.trim()
        @Column("title")
        get() = field.trim()

    @Transient
    var authors: Collection<Author> = emptySet()

    override fun equals(other: Any?) = other != null && other is Book && id == other.id
    override fun hashCode() = id.hashCode()
    override fun toString() = """{"id": "$id", "title": "$title"}"""
}