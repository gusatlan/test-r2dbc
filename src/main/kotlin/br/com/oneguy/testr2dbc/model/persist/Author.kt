package br.com.oneguy.testr2dbc.model.persist

import br.com.oneguy.testr2dbc.utils.cleanCodeText
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("author")
class Author(
    id: Long? = null,
    name: String = ""
) : Comparable<Author> {
    var id = id
        @Id
        @Column("id")
        get

    @Column(value = "name")
    val name = name.trim().uppercase()

    @Transient
    var books: Collection<Book> = emptySet()

    override fun equals(other: Any?) = other != null && other is Author && id == other.id
    override fun hashCode() = id.hashCode()
    override fun toString() = """{"id": "$id", "name": "$name"}"""
    override fun compareTo(other: Author) = name.compareTo(other.name, ignoreCase = true)

}