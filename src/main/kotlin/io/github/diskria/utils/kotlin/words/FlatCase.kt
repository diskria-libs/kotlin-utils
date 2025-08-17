package io.github.diskria.utils.kotlin.words

import io.github.diskria.utils.kotlin.extensions.toWord

object FlatCase : StringCase {
    override fun getWordCase(wordIndex: Int): WordCase = WordCase.LOWERCASE
    override fun splitWords(string: String): List<Word> = listOf(string.toWord())
}
