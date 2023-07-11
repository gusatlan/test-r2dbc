package br.com.oneguy.testr2dbc.model.dto

class AuthorDTO(
    val id: Long? = null,
    name: String = "",
    val books: Collection<BookDTO> = emptySet()
) : Comparable<AuthorDTO> {
    val name = name.trim().uppercase()

    override fun equals(other: Any?) = other != null && other is AuthorDTO && id == other.id
    override fun hashCode() = id.hashCode()
    override fun toString() = """{"id": "$id", "name": "$name"}"""
    override fun compareTo(other: AuthorDTO) = name.compareTo(other.name, ignoreCase = true)

}