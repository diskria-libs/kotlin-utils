package io.github.diskria.utils.kotlin.extensions.generics

inline fun <K, V> Map<K, V>?.ifNullOrEmpty(crossinline fallback: () -> Map<K, V>): Map<K, V> =
    if (isNullOrEmpty()) fallback()
    else this
