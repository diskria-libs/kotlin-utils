package io.github.diskria.utils.kotlin.words

import io.github.diskria.utils.kotlin.Constants
import io.github.diskria.utils.kotlin.extensions.setCase
import io.github.diskria.utils.kotlin.extensions.toWord

sealed class StringCase {

    abstract fun getWordStyle(isFirst: Boolean): WordStyle

    open fun splitWords(string: String): List<Word> = string.split(getWordSeparator()).map { it.toWord() }

    open fun getWordSeparatorChar(): Char? = null

    open fun joinWords(words: List<Word>): String =
        words.mapIndexed { index, word -> word.get(getWordStyle(index == 0)) }.joinToString(getWordSeparator())

    fun matches(string: String): Boolean =
        string.setCase(this, this) == string

    private fun getWordSeparator(): String =
        getWordSeparatorChar()?.toString() ?: Constants.Char.EMPTY
}
