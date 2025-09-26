package io.github.diskria.kotlin.utils.words

import io.github.diskria.kotlin.utils.extensions.common.failWithDetails
import io.github.diskria.kotlin.utils.extensions.toWord
import io.github.diskria.kotlin.utils.properties.toAutoNamedProperty

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
