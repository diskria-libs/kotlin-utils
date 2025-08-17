package io.github.diskria.utils.kotlin.words

sealed class SeparatedCase(
    private val wordSeparator: Char,
    private val wordCase: WordCase = WordCase.LOWERCASE
) : StringCase {
    override fun getWordSeparatorChar(): Char = wordSeparator
    override fun getWordCase(wordIndex: Int): WordCase = wordCase
}
