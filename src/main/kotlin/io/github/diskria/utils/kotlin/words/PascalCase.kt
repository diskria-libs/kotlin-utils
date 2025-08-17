package io.github.diskria.utils.kotlin.words

object PascalCase : StringCase {
    override fun splitWords(string: String): List<Word> = CamelCase.splitWords(string)
    override fun getWordCase(wordIndex: Int): WordCase = WordCase.CAPITALIZED
}
