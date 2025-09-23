package io.github.diskria.kotlin.utils.extensions.primitives

fun Byte.toHex(): String =
    "%02x".format(this)
