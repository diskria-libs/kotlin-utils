package io.github.diskria.kotlin.utils.extensions

import io.github.diskria.kotlin.utils.Constants
import io.github.diskria.kotlin.utils.extensions.common.to
import io.github.diskria.kotlin.utils.extensions.generics.second
import io.github.diskria.kotlin.utils.extensions.generics.secondOrNull
import io.github.diskria.kotlin.utils.extensions.generics.third
import io.github.diskria.kotlin.utils.extensions.generics.thirdOrNull
import io.github.diskria.kotlin.utils.extensions.primitives.nonNegativeOrNull

fun CharSequence.isEndsWithNewLine(): Boolean =
    endsWith(Constants.Char.NEW_LINE)

fun CharSequence.indexOfOrNull(char: Char): Int? =
    indexOf(char).nonNegativeOrNull()

fun CharSequence.lastIndexOfOrNull(char: Char): Int? =
    lastIndexOf(char).nonNegativeOrNull()

fun CharSequence.indexOfOrNull(string: String): Int? =
    indexOf(string).nonNegativeOrNull()

fun CharSequence.indexOfFirstOrNull(
    startIndex: Int = 0,
    predicate: (Char) -> Boolean,
): Int? =
    (startIndex until length).firstOrNull { predicate(this[it]) }

fun CharSequence.lastIndexOfOrNull(string: String): Int? =
    lastIndexOf(string).nonNegativeOrNull()

fun CharSequence.getNextOrNull(fromIndex: Int): Char? =
    getOrNull(fromIndex.inc())

fun CharSequence.remove(regex: Regex): String =
    replace(regex, Constants.Char.EMPTY)

fun CharSequence.splitToPair(delimiter: Char): Pair<String?, String?> =
    split(delimiter, limit = 2).let { it.firstOrNull() to it.secondOrNull() }

fun CharSequence.splitToTriple(delimiter: Char): Triple<String?, String?, String?> =
    split(delimiter, limit = 3).let { it.firstOrNull() to it.secondOrNull() to it.thirdOrNull() }

fun CharSequence.splitExactOrNull(delimiter: Char, size: Int): List<String>? =
    split(delimiter).takeIf { it.size == size }

fun CharSequence.splitExactOrNull(delimiter: String, size: Int): List<String>? =
    split(delimiter).takeIf { it.size == size }

fun CharSequence.splitExactOrNull(delimiter: Regex, size: Int): List<String>? =
    split(delimiter).takeIf { it.size == size }

fun CharSequence.splitToPairOrNull(delimiter: Char): Pair<String, String>? =
    splitExactOrNull(delimiter, 2)?.run { first() to second() }

fun CharSequence.splitToPairOrNull(delimiter: String): Pair<String, String>? =
    splitExactOrNull(delimiter, 2)?.run { first() to second() }

fun CharSequence.splitToPairOrNull(regex: Regex): Pair<String, String>? =
    splitExactOrNull(regex, 2)?.run { first() to second() }

fun CharSequence.splitToTripleOrNull(delimiter: Char): Triple<String, String, String>? =
    splitExactOrNull(delimiter, 3)?.run { first() to second() to third() }

fun CharSequence.splitToTripleOrNull(delimiter: String): Triple<String, String, String>? =
    splitExactOrNull(delimiter, 3)?.run { first() to second() to third() }

fun CharSequence.splitToTripleOrNull(regex: Regex): Triple<String, String, String>? =
    splitExactOrNull(regex, 3)?.run { first() to second() to third() }
