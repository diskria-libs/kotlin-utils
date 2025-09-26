package io.github.diskria.kotlin.utils.words

import io.github.diskria.kotlin.utils.extensions.common.`space case`

object TitleCase : SeparatedCase(`space case`.getWordSeparatorChar(), WordStyle.CAPITALIZED)
