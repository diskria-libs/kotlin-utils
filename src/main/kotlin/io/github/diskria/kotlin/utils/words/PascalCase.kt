package io.github.diskria.kotlin.utils.words

import io.github.diskria.kotlin.utils.extensions.common.camelCase

object PascalCase : StringCase {

    override fun splitWords(string: String): List<Word> =
        camelCase.splitWords(string)

    override fun getWordStyle(isFirst: Boolean): WordStyle =
        WordStyle.CAPITALIZED
}
