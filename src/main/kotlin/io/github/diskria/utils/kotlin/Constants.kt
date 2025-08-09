package io.github.diskria.utils.kotlin

import io.github.diskria.utils.kotlin.extensions.common.combineChars
import io.github.diskria.utils.kotlin.extensions.primitives.repeat

object Constants {

    object File {

        const val ROOT_DIRECTORY: String = Char.SLASH.toString()

        val CURRENT_DIRECTORY: String by lazy { combineChars(Char.DOT, Char.SLASH) }
        val PARENT_DIRECTORY: String by lazy { Char.DOT.repeat(2) }

        object Extension {
            const val PROPERTIES = "properties"
            const val APK = "apk"
            const val JSON = "json"
            const val XML = "xml"
            const val TL = "tl"
            const val TXT = "txt"
            const val TOML = "toml"
            const val GRADLE_SCRIPT = "gradle"
            const val KOTLIN_SCRIPT = "kts"
        }
    }

    object Char {
        const val EMPTY: String = ""

        const val SLASH = '/'
        const val BACK_SLASH = '\\'

        const val DOT = '.'
        const val DOLLAR = '$'
        const val COLON = ':'
        const val SEMICOLON = ';'
        const val SPACE = ' '
        const val CARET = '^'
        const val UNDERSCORE = '_'
        const val PIPE = '|'
        const val HYPHEN = '-'
        const val COMMA = ','
        const val QUESTION_MARK = '?'
        const val EXCLAMATION_MARK = '!'
        const val PLUS = '+'
        const val ASTERISK = '*'
        const val HASH = '#'
        const val NEW_LINE = '\n'
        const val TAB = '\t'

        const val SINGLE_QUOTE = '\''
        const val DOUBLE_QUOTE = '\"'
        const val EQUAL_SIGN = '='

        const val OPENING_ROUND_BRACKET = '('
        const val CLOSING_ROUND_BRACKET = ')'
        const val OPENING_SQUARE_BRACKET = '['
        const val CLOSING_SQUARE_BRACKET = ']'
        const val OPENING_CURLY_BRACKET = '{'
        const val CLOSING_CURLY_BRACKET = '}'
        const val OPENING_ANGLE_BRACKET = '<'
        const val CLOSING_ANGLE_BRACKET = '>'
    }

    object Emoji {
        const val INFO = "ℹ️"
        const val CHECK = "✅"
        const val ERROR = "🛑"
        const val LAMP = "💡"
        const val WRENCH = "🔧"
        const val PUZZLE_PIECE = "🧩"
    }

    object Web {
        const val SCHEME_SEPARATOR = "://"

        const val HTTP_SCHEME = "http"
        const val HTTPS_SCHEME = "https"
    }
}
