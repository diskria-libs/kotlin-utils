package io.github.diskria.kotlin.utils.words

import io.github.diskria.kotlin.utils.Constants
import io.github.diskria.kotlin.utils.extensions.toWord

sealed interface StringCase {

    val isConvertible: Boolean
        get() = true

    fun getWordStyle(isFirst: Boolean): WordStyle

    fun splitWords(string: String): List<Word> =
        string.split(getWordSeparator()).map { it.toWord() }

    fun getWordSeparatorChar(): Char? =
        null

    fun convert(string: String, other: StringCase): String =
        when {
            other == this -> string
            !isConvertible -> error("Conversion from $this is not supported, word boundaries are lost")
            else -> other.joinWords(splitWords(string))
        }

    private fun getWordSeparator(): String =
        getWordSeparatorChar()?.toString() ?: Constants.Char.EMPTY

    private fun joinWords(words: List<Word>): String =
        words.mapIndexed { index, word -> word.get(getWordStyle(index == 0)) }.joinToString(getWordSeparator())
}
