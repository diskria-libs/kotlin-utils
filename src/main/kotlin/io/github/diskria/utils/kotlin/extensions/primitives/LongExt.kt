package io.github.diskria.utils.kotlin.extensions.primitives

fun Long?.orZero(): Long =
    this ?: 0

fun Long.unpackHighInt(): Int =
    (this ushr Int.SIZE_BITS).toInt()

fun Long.unpackLowInt(): Int =
    toInt()
