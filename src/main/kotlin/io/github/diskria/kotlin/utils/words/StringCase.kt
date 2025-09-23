package io.github.diskria.kotlin.utils.words

import io.github.diskria.kotlin.utils.Constants
import io.github.diskria.kotlin.utils.extensions.toWord

sealed interface StringCase {

    fun getWordStyle(isFirst: Boolean): WordStyle

    fun splitWords(string: String): List<Word> = string.split(getWordSeparator()).map { it.toWord() }

    fun getWordSeparatorChar(): Char? = null

    fun joinWords(words: List<Word>): String =
        words.mapIndexed { index, word -> word.get(getWordStyle(index == 0)) }.joinToString(getWordSeparator())

    private fun getWordSeparator(): String =
        getWordSeparatorChar()?.toString() ?: Constants.Char.EMPTY
}
