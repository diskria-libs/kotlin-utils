package io.github.diskria.kotlin.utils.words

import io.github.diskria.kotlin.utils.extensions.common.`space case`

object SentenceCase : SeparatedCase(
    `space case`.getWordSeparatorChar(),
    firstWordStyle = WordStyle.CAPITALIZED,
)
