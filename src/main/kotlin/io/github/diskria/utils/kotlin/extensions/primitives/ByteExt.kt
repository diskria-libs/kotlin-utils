package io.github.diskria.utils.kotlin.extensions.primitives

fun Byte.toHex(): String =
    "%02x".format(this)
