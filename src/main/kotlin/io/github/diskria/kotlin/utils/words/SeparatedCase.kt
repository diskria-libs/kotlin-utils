package io.github.diskria.kotlin.utils.words

sealed class SeparatedCase(
    private val wordSeparator: Char? = null,
    private val wordStyle: WordStyle = WordStyle.LOWERCASE,
    private val firstWordStyle: WordStyle = wordStyle,
) : StringCase {

    override fun getWordSeparatorChar(): Char? =
        wordSeparator

    override fun getWordStyle(isFirst: Boolean): WordStyle =
        if (isFirst) firstWordStyle
        else wordStyle
}
