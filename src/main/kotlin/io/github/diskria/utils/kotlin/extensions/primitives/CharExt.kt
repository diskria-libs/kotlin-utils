package io.github.diskria.utils.kotlin.extensions.primitives

import io.github.diskria.utils.kotlin.Constants
import io.github.diskria.utils.kotlin.EscapeMode
import io.github.diskria.utils.kotlin.extensions.regexEscaped
import io.github.diskria.utils.kotlin.extensions.wrap
import io.github.diskria.utils.kotlin.extensions.wrapWithSpace

fun Char.escaped(escapeMode: EscapeMode): String =
    when (escapeMode) {
        EscapeMode.REGEX -> regexEscaped()

        EscapeMode.REGEX_CHARACTER_CLASS -> {
            when (this) {
                Constants.Char.BACK_SLASH, Constants.Char.CARET,
                Constants.Char.HYPHEN, Constants.Char.CLOSING_SQUARE_BRACKET -> {
                    Constants.Char.BACK_SLASH.toString() + this
                }

                else -> toString()
            }
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
    }

fun Char.repeat(count: Int): String =
    toString().repeat(count)

fun Char.wrap(char: Char): String =
    wrap(char.toString())

fun Char.wrap(string: String): String =
    toString().wrap(string)

fun Char.wrapWithSpace(): String =
    toString().wrapWithSpace()

fun Char.regexEscaped(): String =
    toString().regexEscaped()
