package io.github.diskria.kotlin.utils.words

object PascalCase : StringCase {
    override fun splitWords(string: String): List<Word> = CamelCase.splitWords(string)
    override fun getWordStyle(isFirst: Boolean): WordStyle = WordStyle.CAPITALIZED
}
