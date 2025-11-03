package io.github.diskria.kotlin.utils.extensions.primitives

fun UInt.toHexString(): String =
    toString(16)

infix fun Int.downUntil(to: Int) =
    (this - 1).downTo(to)

fun Int?.orZero(): Int =
    this ?: 0

fun Int.alignPadding(blockSize: Int): Int =
    (blockSize - (this % blockSize)) % blockSize

fun Int.toUnsignedLong(): Long =
    Integer.toUnsignedLong(this)

fun Int.positiveOrNull(): Int? =
    takeIf { it > 0 }

fun Int.negativeOrNull(): Int? =
    takeIf { it < 0 }

fun Int.nonNegativeOrNull(): Int? =
    takeUnless { it < 0 }

fun Int.nonPositiveOrNull(): Int? =
    takeUnless { it > 0 }

fun Int.isMinValue(): Boolean =
    this == Int.MIN_VALUE

fun Int.isMaxValue(): Boolean =
    this == Int.MAX_VALUE

fun Int.isNonNegative(): Boolean =
    !isNegative()

fun Int.isNegative(): Boolean =
    this < 0
