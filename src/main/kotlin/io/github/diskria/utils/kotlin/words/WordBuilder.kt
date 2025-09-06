package io.github.diskria.utils.kotlin.words

import io.github.diskria.utils.kotlin.properties.toAutoNamedProperty
import io.github.diskria.utils.kotlin.extensions.common.failWithDetails
import io.github.diskria.utils.kotlin.extensions.toWord

class WordBuilder {

    private val stringBuilder: StringBuilder by lazy { StringBuilder() }

    fun appendChar(char: Char) {
        if (char.isWhitespace()) {
            failWithDetails("Whitespace is not allowed inside words") {
                val char by char.toAutoNamedProperty()
                val word by buildWord().toAutoNamedProperty()
                listOf(char, word)
            }
        }
        stringBuilder.append(char)
    }

    fun buildWord(): Word =
        stringBuilder.toString().toWord()
}
