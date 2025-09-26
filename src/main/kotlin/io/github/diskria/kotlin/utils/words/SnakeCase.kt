package io.github.diskria.kotlin.utils.words

import io.github.diskria.kotlin.utils.Constants
import io.github.diskria.kotlin.utils.extensions.common.SCREAMING_SNAKE_CASE

object SnakeCase : SeparatedCase(Constants.Char.UNDERSCORE) {

    override fun convert(string: String, other: StringCase): String =
        when (other) {
            SCREAMING_SNAKE_CASE -> string.uppercase()
            else -> super.convert(string, other)
        }
}
