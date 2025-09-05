package io.github.diskria.utils.kotlin.words

object SentenceCase : SeparatedCase(
    SpaceCase.getWordSeparatorChar(),
    WordStyle.LOWERCASE,
    firstWordStyle = WordStyle.CAPITALIZED,
)
