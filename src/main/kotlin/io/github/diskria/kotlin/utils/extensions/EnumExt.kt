package io.github.diskria.kotlin.utils.extensions

import kotlin.enums.enumEntries

inline fun <reified T : Enum<T>> T.previousOrNull(): T? =
    enumEntries<T>().getOrNull(ordinal.dec())

inline fun <reified T : Enum<T>> T.nextOrNull(): T? =
    enumEntries<T>().getOrNull(ordinal.inc())

inline fun <reified T : Enum<T>> T.previousOrSelf(): T =
    previousOrNull() ?: this

inline fun <reified T : Enum<T>> T.nextOrSelf(): T =
    nextOrNull() ?: this

fun Enum<*>.previousEnumOrNull(): Enum<*>? =
    entries.getOrNull(ordinal.dec())

fun Enum<*>.nextEnumOrNull(): Enum<*>? =
    entries.getOrNull(ordinal.inc())

private inline val Enum<*>.entries: List<Enum<*>>
    get() = javaClass.enumConstants.filterIsInstance<Enum<*>>()
