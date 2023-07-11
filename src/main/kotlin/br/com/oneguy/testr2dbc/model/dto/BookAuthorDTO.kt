package br.com.oneguy.testr2dbc.model.dto

data class BookAuthorDTO(
    val id: Long? = null,
    val bookId: Long,
    val authorId: Long
) {
    override fun equals(other: Any?): Boolean {
        return other != null &&
                other is BookAuthorDTO &&
                bookId == other.bookId &&
                authorId == other.authorId
    }

    override fun hashCode() = bookId.hashCode() or authorId.hashCode()
    override fun toString() = """{"id": $id,"bookId": $bookId,"authorId": $authorId}"""
}