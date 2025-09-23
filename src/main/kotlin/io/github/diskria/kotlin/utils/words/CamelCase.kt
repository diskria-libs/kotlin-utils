package io.github.diskria.kotlin.utils.words

import io.github.diskria.kotlin.utils.extensions.generics.foldChainWindow

object CamelCase : StringCase {

    override fun splitWords(string: String): List<Word> =
        string.toList().foldChainWindow(mutableListOf<WordBuilder>()) { prev, current, next ->
            val previousChar = prev()
            if (previousChar == null ||
                previousChar.isLowerCase() && current.isUpperCase() ||
                previousChar.isUpperCase() && current.isUpperCase() && next()?.isLowerCase() == true
            ) {
                add(WordBuilder())
            }
            last().appendChar(current)
        }.map { it.buildWord() }

    override fun getWordStyle(isFirst: Boolean): WordStyle =
        if (isFirst) WordStyle.LOWERCASE
        else WordStyle.CAPITALIZED
}
