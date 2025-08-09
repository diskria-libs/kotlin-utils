package io.github.diskria.utils.kotlin.extensions

import io.github.diskria.utils.kotlin.BracketsType
import io.github.diskria.utils.kotlin.Constants
import io.github.diskria.utils.kotlin.EscapeMode
import io.github.diskria.utils.kotlin.delegates.toAutoNamedPair
import io.github.diskria.utils.kotlin.extensions.common.failWithDetails
import io.github.diskria.utils.kotlin.extensions.common.failWithInvalidValue
import io.github.diskria.utils.kotlin.extensions.common.modifyUnless
import io.github.diskria.utils.kotlin.extensions.primitives.escaped
import java.io.File

inline fun <reified T> String.toTypedOrThrow(): T =
    toTypedOrNull() ?: failWithDetails {
        val string by this.toAutoNamedPair()
        val type by T::class.toAutoNamedPair()
        listOf(string, type)
    }

inline fun String?.ifNullOrEmpty(
    crossinline fallback: () -> String,
): String =
    if (isNullOrEmpty()) fallback()
    else this

inline fun Sequence<String>.dropUntil(
    crossinline predicate: (String) -> Boolean
): Sequence<String> =
    dropWhile { !predicate(it) }

fun Sequence<String>.skipUntilAfter(marker: String): Sequence<String> =
    dropUntil { marker in it }.drop(1)

fun String.capitalizeFirst(): String =
    lowercase().replaceFirstChar { it.uppercaseChar() }

fun String.equalsIgnoreCase(other: String?): Boolean =
    equals(other, ignoreCase = true)

fun String.toCharOrThrow(): Char =
    toCharOrNull() ?: failWithInvalidValue(this)

fun String.splitByComma(): List<String> =
    split(Constants.Char.COMMA)

fun String.splitBySpace(): List<String> =
    split(Constants.Char.SPACE)

fun String.invertCase(): String =
    map { char ->
        if (char.isLowerCase()) char.uppercaseChar()
        else char.lowercaseChar()
    }.toString()

fun String.wrap(char: Char): String =
    char + this + char

fun String.wrap(string: String): String =
    string + this + string

fun String.wrapWithBrackets(
    bracketsType: BracketsType?,
    count: Int = 1,
    escapeMode: EscapeMode? = null,
): String {
    if (bracketsType == null) {
        return this
    }
    val (openingSymbol, closingSymbol) = if (escapeMode != null) {
        bracketsType.openingChar.escaped(escapeMode) to bracketsType.closingChar.escaped(escapeMode)
    } else {
        bracketsType.openingChar.toString() to bracketsType.closingChar.toString()
    }
    return openingSymbol.repeat(count) + this + closingSymbol.repeat(count)
}

fun String.wrapWithSingleQuote(): String =
    wrap(Constants.Char.SINGLE_QUOTE)

fun String.wrapWithDoubleQuote(): String =
    wrap(Constants.Char.DOUBLE_QUOTE)

fun String.wrapWithSpace(): String =
    wrap(Constants.Char.SPACE)

fun String.replace(oldValue: Char, newValue: String): String =
    replace(oldValue.toString(), newValue)

fun String.replace(regex: Regex, char: Char): String =
    replace(regex, char.toString())

fun String.appendPackageName(packageName: String): String =
    this + packageName.ensurePrefix(Constants.Char.DOT)

fun String.ensurePrefix(prefix: Char): String =
    ensurePrefix(prefix.toString())

fun String.ensureSuffix(suffix: Char): String =
    ensureSuffix(suffix.toString())

fun String.ensurePrefix(prefix: String): String =
    modifyUnless(startsWith(prefix)) { prefix + this }

fun String.ensureSuffix(suffix: String): String =
    modifyUnless(endsWith(suffix)) { this + suffix }

fun String.ensureSurrounding(prefix: String, suffix: String): String =
    ensurePrefix(prefix).ensureSuffix(suffix)

fun String.toFile(): File =
    File(this)

fun String.regexEscaped(): String =
    Regex.escape(this)

fun String.insertAt(index: Int, insertion: String): String =
    substring(0, index) + insertion + substring(index)

fun String.toIntOrThrow(): Int =
    toIntOrNull() ?: failWithInvalidValue(this)

fun String.toUIntFromHex(): UInt =
    toUInt(16)

fun String.collapseJoin(other: String, separator: Char): String =
    listOf(
        trimEnd(separator),
        other.trimStart(separator),
    ).joinToString(separator.toString())

fun String.removePrefix(char: Char): String =
    removePrefix(char.toString())

fun String.removeSuffix(char: Char): String =
    removeSuffix(char.toString())

fun String.toUnixPath(): String =
    replace(File.separatorChar, Constants.Char.SLASH)

fun String.collapseRepeating(segment: String): String {
    val repeated = segment.repeat(2)
    return generateSequence(this) { previous ->
        val next = previous.replace(repeated, segment)
        if (next != previous) next else null
    }.last()
}

inline fun <reified T> String.toTypedOrNull(): T? =
    when (T::class) {
        Boolean::class -> toBooleanOrNull()
        Int::class -> toIntOrNull()
        Long::class -> toLongOrNull()
        Float::class -> toFloatOrNull()
        Double::class -> toDoubleOrNull()
        Char::class -> toCharOrNull()
        String::class -> this
        else -> null
    } as T?

fun String.toCharOrNull(): Char? =
    singleOrNull()

fun String.toBooleanOrNull(): Boolean? =
    toBooleanStrictOrNull()
