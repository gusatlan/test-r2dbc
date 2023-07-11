package br.com.oneguy.testr2dbc.utils

import java.util.*

fun createId() = UUID.randomUUID().toString().trim().lowercase()

fun cleanCode(text: String?): String {
    return if (text != null) {
        val pattern = "\\D".toRegex()
        pattern.replace(text.trim(), "")
    } else {
        ""
    }
}

fun cleanCodeText(text: String?): String {
    return if (text != null) {
        val pattern = "\\W".toRegex()
        pattern.replace(text.trim(), "").lowercase()
    } else {
        ""
    }
}

fun clean(text: String?): String {
    return text?.trim()?.lowercase() ?: ""
}