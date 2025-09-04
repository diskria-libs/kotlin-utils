package io.github.diskria.utils.kotlin.extensions

import io.github.diskria.utils.kotlin.Constants
import java.nio.file.Path

val Path.nameWithoutExtension: String
    get() = fileName.toString().substringBeforeLast(Constants.Char.DOT)

val Path.extension: String
    get() = fileName.toString().substringAfterLast(Constants.Char.DOT)
