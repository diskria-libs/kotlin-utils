package io.github.diskria.utils.kotlin.words

object PascalCase : StringCase() {
    override fun splitWords(string: String): List<Word> = CamelCase.splitWords(string)
    override fun getWordStyle(isFirst: Boolean): WordStyle = WordStyle.CAPITALIZED
}
