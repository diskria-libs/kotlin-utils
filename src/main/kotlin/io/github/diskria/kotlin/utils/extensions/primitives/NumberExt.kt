package io.github.diskria.kotlin.utils.extensions.primitives

inline fun <T> T.coerceBelow(limit: T, fallback: () -> T): T where T : Number, T : Comparable<T> =
    if (this < limit) this else fallback()

inline fun <T> T.coerceAbove(limit: T, fallback: () -> T): T where T : Number, T : Comparable<T> =
    if (limit < this) this else fallback()
