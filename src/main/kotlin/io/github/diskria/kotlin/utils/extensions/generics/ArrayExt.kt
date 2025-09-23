package io.github.diskria.kotlin.utils.extensions.generics

inline fun <E> Array<E>?.ifNullOrEmpty(crossinline fallback: () -> Array<E>): Array<E> =
    if (isNullOrEmpty()) fallback()
    else this

fun <T> Array<T>.joinToString(separator: Char, transform: ((T) -> CharSequence)? = null): String =
    joinToString(separator.toString(), transform = transform)
