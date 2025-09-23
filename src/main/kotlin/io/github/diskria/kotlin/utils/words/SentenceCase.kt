package io.github.diskria.kotlin.utils.words

object SentenceCase : SeparatedCase(
    SpaceCase.getWordSeparatorChar(),
    WordStyle.LOWERCASE,
    firstWordStyle = WordStyle.CAPITALIZED,
)
