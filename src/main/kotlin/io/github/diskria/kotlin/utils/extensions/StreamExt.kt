package io.github.diskria.kotlin.utils.extensions

import java.io.InputStream

fun InputStream.readText(): String =
    bufferedReader().readText()

fun InputStream.readOrNull(byteArray: ByteArray): Int? =
    read(byteArray).takeIf { it != -1 }
