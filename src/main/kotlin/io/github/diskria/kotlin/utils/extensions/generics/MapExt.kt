package io.github.diskria.kotlin.utils.extensions.generics

inline fun <K, V> Map<K, V>?.ifNullOrEmpty(crossinline fallback: () -> Map<K, V>): Map<K, V> =
    if (isNullOrEmpty()) fallback()
    else this

fun <K, V> Map<K, V>?.toNullIfEmpty(): Map<K, V>? =
    this?.ifEmpty { null }
