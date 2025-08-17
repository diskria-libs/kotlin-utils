package io.github.diskria.utils.kotlin.words

import io.github.diskria.utils.kotlin.extensions.toWord

class WordBuilder {
    private val stringBuilder: StringBuilder = StringBuilder()

    fun appendChar(char: Char) {
        stringBuilder.append(char)
    }

    fun buildWord(): Word = stringBuilder.toString().toWord()
}
