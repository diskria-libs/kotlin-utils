package io.github.diskria.kotlin.utils.extensions.generics

import io.github.diskria.kotlin.utils.extensions.common.failWithDetails
import io.github.diskria.kotlin.utils.properties.autoNamedProperty

fun <T> List<T>.takeIfExceeds(limit: Int): List<T>? =
    if (size > limit) take(size - limit)
    else null

fun <T> List<T>.second(): T =
    secondOrNull() ?: failWithDetails("Can't get second element of list") {
        val list by this.autoNamedProperty()
        listOf(list)
    }

fun <T> List<T>.third(): T =
    thirdOrNull() ?: failWithDetails("Can't get third element of list") {
        val list by this.autoNamedProperty()
        listOf(list)
    }

fun <T> List<T>.secondOrNull(): T? =
    getOrNull(1)

fun <T> List<T>.thirdOrNull(): T? =
    getOrNull(2)

fun <T> MutableList<T>.modifyFirst(transform: (T) -> T) =
    modifyElementAt(0, transform)

fun <T> MutableList<T>.modifyLast(transform: (T) -> T) =
    modifyElementAt(lastIndex, transform)

fun <T> MutableList<T>.modifyElementAt(index: Int, transform: (T) -> T) {
    this[index] = transform(this[index])
}

fun <T> List<T>?.toNullIfEmpty(): List<T>? =
    this?.ifEmpty { null }
