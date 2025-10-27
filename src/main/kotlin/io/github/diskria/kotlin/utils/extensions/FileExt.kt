package io.github.diskria.kotlin.utils.extensions

import io.github.diskria.kotlin.utils.Constants
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
        generateSequence { stream.readOrNull(buffer) }.forEach { bytesRead ->
            digest.update(buffer, 0, bytesRead)
        }
    }
    return digest.digest().asIterable().toFlatString { byte -> byte.toHex() }
}

inline fun <T> File.ifNotExists(fallback: (File) -> T): T? =
    takeUnless { exists() }?.let(fallback)

fun File.ensureDeleted(): File {
    if (exists() && !delete()) {
        error("Failed to delete $path")
    }
    return this
}

fun File.ensureDirectoryExists(): File {
    if (!isDirectory && !mkdirs()) {
        error("Failed to create directory $path")
    }
    return this
}

fun File.ensureFileExists(): File {
    parentFile.ensureDirectoryExists()
    if (!isFile && !createNewFile()) {
        error("Failed to create file $path")
    }
    return this
}

fun File.findUpwardsOrNull(path: String, allowHidden: Boolean = false, filter: (File) -> Boolean): File? =
    generateSequence(absoluteFile) { it.parentFile }
        .map { it.resolve(path) }
        .firstOrNull { filter(it) && (allowHidden || !it.isHidden) }

fun File.findDirectoryUpwards(
    path: String,
    allowHidden: Boolean = false,
    onNotFound: (String) -> Nothing = { error("Directory ${it.wrapWithSingleQuote()} not found upwards") }
): File =
    findUpwardsOrNull(path, allowHidden) { it.isDirectory } ?: onNotFound(path)

fun File.findFileUpwards(
    path: String,
    allowHidden: Boolean = false,
    onNotFound: (String) -> Nothing = { error("File ${it.wrapWithSingleQuote()} not found upwards") }
): File =
    findUpwardsOrNull(path, allowHidden) { it.isFile } ?: onNotFound(path)

fun File.listFilesWithExtension(extension: String, allowHidden: Boolean = false): List<File> =
    absoluteFile.listFiles {
        it.isFile &&
                (allowHidden || !it.isHidden) &&
                it.extension.equalsIgnoreCase(extension.removePrefix(Constants.Char.DOT))
    }.orEmpty().toList()

fun File.listDirectories(allowHidden: Boolean = false): List<File> =
    listFiles { it.isDirectory && (allowHidden || !it.isHidden) }.orEmpty().toList()
