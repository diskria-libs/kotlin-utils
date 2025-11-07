package io.github.diskria.kotlin.utils.helpers

import io.github.diskria.kotlin.utils.Constants

enum class LineEndingModeType(val char: String) {
    CR(Constants.Char.NEW_LINE_CR.toString()),
    LF(Constants.Char.NEW_LINE.toString()),
    CRLF(Constants.Char.NEW_LINE_CRLF),
}
