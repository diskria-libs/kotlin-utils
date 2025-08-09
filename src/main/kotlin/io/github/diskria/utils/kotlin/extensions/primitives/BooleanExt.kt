package io.github.diskria.utils.kotlin.extensions.primitives

import java.util.concurrent.atomic.AtomicBoolean

fun Boolean.toInt(): Int =
    if (this) 1
    else 0

val AtomicBoolean.isTrue
    get() = get()

val AtomicBoolean.isFalse
    get() = !isTrue

fun AtomicBoolean.setTrue() =
    set(true)

fun AtomicBoolean.setFalse() =
    set(false)
