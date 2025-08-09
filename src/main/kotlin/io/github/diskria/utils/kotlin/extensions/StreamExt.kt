package io.github.diskria.utils.kotlin.extensions

import java.io.InputStream

fun InputStream.readText(): String =
    bufferedReader().readText()

fun InputStream.readOrNull(byteArray: ByteArray): Int? =
    read(byteArray).takeIf { it != -1 }
