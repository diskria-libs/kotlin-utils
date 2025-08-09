package io.github.diskria.utils.kotlin.extensions

import io.github.diskria.utils.kotlin.extensions.generics.toFlatString
import io.github.diskria.utils.kotlin.extensions.libraries.jsonFor
import io.github.diskria.utils.kotlin.extensions.primitives.toHex
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
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

fun File.asDirectoryOrNull(): File? =
    takeIf { isDirectory }

fun File.asFileOrThrow(): File =
    asFileOrNull() ?: error("File $path not exists")

fun File.asDirectoryOrThrow(): File =
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

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> File.serialize(): T =
    jsonFor<T>().decodeFromStream(inputStream())
