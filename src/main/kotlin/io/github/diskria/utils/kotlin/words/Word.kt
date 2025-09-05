package io.github.diskria.utils.kotlin.words

import io.github.diskria.utils.kotlin.extensions.capitalizeFirstChar

@JvmInline
value class Word(private val string: String) {

    fun get(case: WordStyle): String =
        with(string) {
            when (case) {
                WordStyle.LOWERCASE -> lowercase()
                WordStyle.UPPERCASE -> uppercase()
                WordStyle.CAPITALIZED -> capitalizeFirstChar()
            }
        }
}
