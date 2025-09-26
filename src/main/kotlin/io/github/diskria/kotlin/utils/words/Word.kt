package io.github.diskria.kotlin.utils.words

import io.github.diskria.kotlin.utils.extensions.capitalizeFirstChar

@JvmInline
value class Word(private val string: String) {

    fun get(case: WordStyle): String =
        with(string) {
            when (case) {
                WordStyle.LOWERCASE -> lowercase()
                WordStyle.UPPERCASE -> uppercase()
                WordStyle.CAPITALIZED -> capitalizeFirstChar(lowerRest = true)
            }
        }
}
