package io.github.diskria.utils.kotlin.extensions.primitives

import io.github.diskria.utils.kotlin.Constants
import io.github.diskria.utils.kotlin.EscapeMode
import io.github.diskria.utils.kotlin.extensions.appendPrefix
import io.github.diskria.utils.kotlin.extensions.common.mapIfAnyOf
import io.github.diskria.utils.kotlin.extensions.regexEscaped
import io.github.diskria.utils.kotlin.extensions.wrap
import io.github.diskria.utils.kotlin.extensions.wrapWithSpace

fun Char.escaped(escapeMode: EscapeMode?): String =
    when (escapeMode) {
        EscapeMode.REGEX -> regexEscaped()

        EscapeMode.REGEX_CHARACTER_CLASS -> {
            mapIfAnyOf(
                Constants.Char.BACK_SLASH,
                Constants.Char.CARET,
                Constants.Char.HYPHEN,
                Constants.Char.CLOSING_SQUARE_BRACKET,
                elseValue = toString(),
            ) { it.toString().appendPrefix(Constants.Char.BACK_SLASH.toString()) }
        }

        EscapeMode.LITERAL -> when (this) {
            Constants.Char.SINGLE_QUOTE -> "\\'"
            Constants.Char.DOUBLE_QUOTE -> "\\\""
            Constants.Char.BACK_SLASH -> "\\\\"
            Constants.Char.NEW_LINE -> "\\n"
            Constants.Char.TAB -> "\\t"
            '\r' -> "\\r"
            '\b' -> "\\b"
            '\u000C' -> "\\f"
            else -> toString()
        }

        null -> toString()
    }

fun Char.repeat(count: Int): String =
    toString().repeat(count)

fun Char.wrap(char: Char, count: Int = 1): String =
    wrap(char.toString(), count)

fun Char.wrap(string: String, count: Int = 1): String =
    toString().wrap(string, count)

fun Char.wrapWithSpace(count: Int = 1): String =
    toString().wrapWithSpace(count)

fun Char.regexEscaped(): String =
    toString().regexEscaped()
