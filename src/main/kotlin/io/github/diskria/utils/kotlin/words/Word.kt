package io.github.diskria.utils.kotlin.words

import io.github.diskria.utils.kotlin.extensions.capitalizeFirstChar

@JvmInline
value class Word(private val string: String) {
    fun get(case: WordCase): String =
        with(string) {
            when (case) {
                WordCase.LOWERCASE -> lowercase()
                WordCase.UPPERCASE -> uppercase()
                WordCase.CAPITALIZED -> capitalizeFirstChar()
            }
        }
}
