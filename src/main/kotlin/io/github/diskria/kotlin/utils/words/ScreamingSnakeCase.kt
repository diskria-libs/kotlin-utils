package io.github.diskria.kotlin.utils.words

import io.github.diskria.kotlin.utils.extensions.common.snake_case

object ScreamingSnakeCase : SeparatedCase(snake_case.getWordSeparatorChar(), WordStyle.UPPERCASE) {

    override fun convert(string: String, other: StringCase): String =
        when (other) {
            snake_case -> string.lowercase()
            else -> super.convert(string, other)
        }
}
