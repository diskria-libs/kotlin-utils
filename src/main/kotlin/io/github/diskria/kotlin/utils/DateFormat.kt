package io.github.diskria.kotlin.utils

@Suppress("SpellCheckingInspection")
enum class DateFormat(val pattern: String) {
    ISO_DATE("yyyy-MM-dd"),
    TIME_SECONDS("HH:mm:ss"),
    ISO_DATE_TIME("yyyy-MM-dd'T'HH:mm:ss"),
    ISO_DATE_TIME_MILLIS("yyyy-MM-dd'T'HH:mm:ss.SSS")
}
