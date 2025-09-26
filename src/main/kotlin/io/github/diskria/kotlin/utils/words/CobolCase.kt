package io.github.diskria.kotlin.utils.words

import io.github.diskria.kotlin.utils.extensions.common.`kebab-case`

object CobolCase : SeparatedCase(`kebab-case`.getWordSeparatorChar(), WordStyle.UPPERCASE)
