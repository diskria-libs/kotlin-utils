package io.github.diskria.kotlin.utils.words

import io.github.diskria.kotlin.utils.extensions.common.`kebab-case`

object TrainCase : SeparatedCase(`kebab-case`.getWordSeparatorChar(), WordStyle.CAPITALIZED)
