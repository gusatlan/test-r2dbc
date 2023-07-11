package br.com.oneguy.testr2dbc.model.persist

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("book_author")
class BookAuthor(
    id: Long? = null,
    bookId: Long,
    authorId: Long
) {
    var id = id
        @Id
        @Column("id")
        get

    val bookId = bookId
        @Column("book")
        get

    val authorId = authorId
        @Column("author")
        get

    override fun equals(other: Any?): Boolean {
        return other != null &&
                other is BookAuthor &&
                bookId == other.bookId &&
                authorId == other.authorId
    }

    override fun hashCode() = bookId.hashCode() or authorId.hashCode()
    override fun toString() = """{"id": $id,"bookId": $bookId,"authorId": $authorId}"""
}