package io.github.diskria.utils.kotlin.words

import io.github.diskria.utils.kotlin.Constants
import io.github.diskria.utils.kotlin.extensions.toWord

sealed interface StringCase {
    fun getWordCase(wordIndex: Int): WordCase
    fun splitWords(string: String): List<Word> = string.split(getWordSeparator()).map { it.toWord() }
    fun getWordSeparatorChar(): Char? = null

    fun joinWords(words: List<Word>): String =
        words.mapIndexed { index, word -> word.get(getWordCase(index)) }.joinToString(getWordSeparator())

    private fun getWordSeparator(): String = getWordSeparatorChar()?.toString() ?: Constants.Char.EMPTY
}
