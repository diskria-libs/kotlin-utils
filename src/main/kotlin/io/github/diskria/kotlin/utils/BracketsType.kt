package io.github.diskria.kotlin.utils

enum class BracketsType(
    val openingChar: Char,
    val closingChar: Char,
) {
    ROUND(
        Constants.Char.OPENING_ROUND_BRACKET,
        Constants.Char.CLOSING_ROUND_BRACKET
    ),
    SQUARE(
        Constants.Char.OPENING_SQUARE_BRACKET,
        Constants.Char.CLOSING_SQUARE_BRACKET
    ),
    CURLY(
        Constants.Char.OPENING_CURLY_BRACKET,
        Constants.Char.CLOSING_CURLY_BRACKET
    ),
    ANGLE(
        Constants.Char.OPENING_ANGLE_BRACKET,
        Constants.Char.CLOSING_ANGLE_BRACKET
    ),
}
