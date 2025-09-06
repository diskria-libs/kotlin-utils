package io.github.diskria.utils.kotlin.extensions

import io.github.diskria.utils.kotlin.BracketsType
import io.github.diskria.utils.kotlin.Constants
import io.github.diskria.utils.kotlin.EscapeMode
import io.github.diskria.utils.kotlin.Semver
import io.github.diskria.utils.kotlin.properties.toAutoNamedProperty
import io.github.diskria.utils.kotlin.extensions.common.failWithDetails
import io.github.diskria.utils.kotlin.extensions.common.failWithInvalidValue
import io.github.diskria.utils.kotlin.extensions.common.modifyIf
import io.github.diskria.utils.kotlin.extensions.common.modifyUnless
import io.github.diskria.utils.kotlin.extensions.generics.toFlatString
import io.github.diskria.utils.kotlin.extensions.primitives.escaped
import io.github.diskria.utils.kotlin.words.StringCase
import io.github.diskria.utils.kotlin.words.Word
import java.io.File
import kotlin.enums.enumEntries

inline fun <reified T> String.toTypedOrThrow(): T =
    toTypedOrNull() ?: failWithDetails {
        val string by this.toAutoNamedProperty()
        val type by T::class.toAutoNamedProperty()
        listOf(string, type)
    }

inline fun String?.ifNullOrEmpty(crossinline fallback: () -> String): String =
    if (isNullOrEmpty()) fallback()
    else this

inline fun Sequence<String>.dropUntil(crossinline predicate: (String) -> Boolean): Sequence<String> =
    dropWhile { !predicate(it) }

fun Sequence<String>.skipUntilAfter(marker: String): Sequence<String> =
    dropUntil { marker in it }.drop(1)

fun String.capitalizeFirstChar(lowerRest: Boolean = false): String =
    replaceFirstChar { if (it.isLowerCase()) it.titlecaseChar() else it }
        .modifyIf(lowerRest) { it.first() + it.drop(1).lowercase() }

fun String.equalsIgnoreCase(other: String?): Boolean =
    equals(other, ignoreCase = true)

fun String.toCharOrThrow(): Char =
    toCharOrNull() ?: failWithInvalidValue(this)

fun String.splitByComma(): List<String> =
    split(Constants.Char.COMMA)

fun String.splitBySpace(): List<String> =
    split(Constants.Char.SPACE)

fun String.invertCase(): String =
    rebuild { char ->
        if (char.isLowerCase()) char.titlecase()
        else char.lowercaseChar()
    }

fun String.wrap(char: Char, count: Int = 1): String =
    wrap(char.toString(), count)

fun String.wrap(string: String, count: Int = 1): String =
    string.repeat(count).let { surround(it, it) }

fun String.wrapWithBrackets(
    bracketsType: BracketsType?,
    count: Int = 1,
    escapeMode: EscapeMode? = null,
): String {
    if (bracketsType == null) {
        return this
    }
    return ensureSurrounding(
        prefix = bracketsType.openingChar.escaped(escapeMode).repeat(count),
        suffix = bracketsType.closingChar.escaped(escapeMode).repeat(count)
    )
}

fun String.wrapWithSingleQuote(count: Int = 1): String =
    wrap(Constants.Char.SINGLE_QUOTE, count)

fun String.wrapWithDoubleQuote(count: Int = 1): String =
    wrap(Constants.Char.DOUBLE_QUOTE, count)

fun String.wrapWithSpace(count: Int = 1): String =
    wrap(Constants.Char.SPACE, count)

fun String.replace(oldValue: Char, newValue: String): String =
    replace(oldValue.toString(), newValue)

fun String.replace(regex: Regex, char: Char): String =
    replace(regex, char.toString())

fun String.appendPackageName(packageName: String): String =
    appendSuffix(packageName.ensurePrefix(Constants.Char.DOT))

fun String.ensurePrefix(prefix: Char): String =
    ensurePrefix(prefix.toString())

fun String.ensureSuffix(suffix: Char): String =
    ensureSuffix(suffix.toString())

fun String.ensurePrefix(prefix: String): String =
    modifyUnless(startsWith(prefix)) { appendPrefix(prefix) }

fun String.ensureSuffix(suffix: String): String =
    modifyUnless(endsWith(suffix)) { appendSuffix(suffix) }

fun String.ensureSurrounding(prefix: String, suffix: String): String =
    ensurePrefix(prefix).ensureSuffix(suffix)

fun String.appendPrefix(prefix: String): String =
    prefix + this

fun String.appendSuffix(suffix: String): String =
    this + suffix

fun String.surround(prefix: String, suffix: String): String =
    appendPrefix(prefix).appendSuffix(suffix)

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

fun String.toSemver(): Semver =
    Semver.of(this)

fun String.toWord(): Word = Word(this)

fun String.setCase(oldCase: StringCase, newCase: StringCase): String =
    if (oldCase == newCase) this
    else newCase.joinWords(splitWords(oldCase))

fun String.splitWords(case: StringCase): List<Word> =
    case.splitWords(this)

fun <R> String.rebuild(transform: (Char) -> R): String =
    map(transform).toFlatString()

inline fun <reified T : Enum<T>> String.toEnumOrNull(): T? =
    enumEntries<T>().firstOrNull { it.name.equalsIgnoreCase(this) }

inline fun <reified T : Enum<T>> String.toEnum(): T =
    toEnumOrNull<T>() ?: failWithDetails("Unknown enum name") {
        val enumName by this.toAutoNamedProperty()
        val availableValues by enumEntries<T>().toAutoNamedProperty()
        listOf(enumName, availableValues)
    }

fun String?.toNullIfEmpty(): String? =
    this?.ifEmpty { null }
