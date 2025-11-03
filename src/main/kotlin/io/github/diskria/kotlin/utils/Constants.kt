package io.github.diskria.kotlin.utils

import io.github.diskria.kotlin.utils.extensions.common.combineChars
import io.github.diskria.kotlin.utils.extensions.primitives.repeat

object Constants {

    object File {

        object Path {
            const val ROOT_DIRECTORY: String = Char.SLASH.toString()
            val CURRENT_DIRECTORY: String = combineChars(Char.DOT, Char.SLASH)
            val PARENT_DIRECTORY: String = Char.DOT.repeat(2) + Char.SLASH
        }

        object Extension {
            const val PROPERTIES = "properties"
            const val TOML = "toml"
            const val MARKDOWN = "md"
            const val TL = "tl"
            const val APK = "apk"
            const val JAR = "jar"
            const val GRADLE_SCRIPT = "gradle"
            const val JAVA = "java"
            const val KOTLIN = "kt"
            const val KOTLIN_SCRIPT = "kts"

            const val TXT = "txt"
            const val XML = "xml"
            const val JSON = "json"
            const val HTML = "html"
            const val PNG = "png"
            const val SVG = "svg"
        }
    }

    object Char {
        const val EMPTY: String = ""
        const val SPACE = ' '
        const val NEW_LINE = '\n'
        const val TAB = '\t'

        const val SLASH = '/'
        const val BACK_SLASH = '\\'

        const val DOT = '.'
        const val COMMA = ','
        const val COLON = ':'
        const val SEMICOLON = ';'
        const val CARET = '^'
        const val UNDERSCORE = '_'
        const val VERTICAL_BAR = '|'
        const val ASTERISK = '*'
        const val AT_SIGN = '@'
        const val NUMBER_SIGN = '#'
        const val DOLLAR = '$'

        const val HYPHEN = '-'
        const val EM_DASH = '—'
        const val EN_DASH = '–'
        const val PLUS = '+'

        const val QUESTION_MARK = '?'
        const val EXCLAMATION_MARK = '!'

        const val PERCENT_SIGN = '%'
        const val EQUAL_SIGN = '='

        const val BACKTICK = '`'
        const val SINGLE_QUOTE = '\''
        const val DOUBLE_QUOTE = '"'

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
        val SCHEME_SEPARATOR: String = Char.COLON + Char.SLASH.repeat(2)

        const val HTTP_SCHEME = "http"
        const val HTTPS_SCHEME = "https"
    }
}
