package io.github.diskria.kotlin.utils.extensions

import io.github.diskria.kotlin.utils.extensions.generics.toFlatString
import io.github.diskria.kotlin.utils.extensions.primitives.toHex
import java.io.File
import java.security.MessageDigest

inline fun File.readByLines(readLine: (String) -> Unit) =
    bufferedReader().useLines { lines ->
        lines.forEach { line ->
            readLine(line)
        }
    }

inline fun File.readByLinesIndexed(readLine: (Int, String) -> Unit) =
    bufferedReader().useLines { lines ->
        lines.forEachIndexed { lineIndex, line ->
            readLine(lineIndex, line)
        }
    }

fun File.findLastModifiedFile(extension: String): File? =
    walkTopDown()
        .filter { it.isFile && it.extension.equalsIgnoreCase(extension) }
        .maxByOrNull { it.lastModified() }

fun File.asFileOrNull(): File? =
    takeIf { isFile }

fun File.asFile(): File =
    asFileOrNull() ?: error("File $path not exists")

fun File.asDirectoryOrNull(): File? =
    takeIf { isDirectory }

fun File.asDirectory(): File =
    asDirectoryOrNull() ?: error("Directory $path not exists")

fun File.getChecksum(algorithmName: String = "MD5"): String {
    val digest = MessageDigest.getInstance(algorithmName)
    inputStream().use { stream ->
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        generateSequence { stream.readOrNull(buffer) }
            .forEach { bytesRead -> digest.update(buffer, 0, bytesRead) }
    }
    return digest.digest().asIterable().toFlatString { byte -> byte.toHex() }
}

fun File.deleteOrThrow() {
    if (exists() && !delete()) {
        error("Failed to delete file: $absolutePath")
    }
}
