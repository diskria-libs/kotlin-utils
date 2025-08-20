package io.github.diskria.utils.kotlin.extensions.common

import io.github.diskria.utils.kotlin.Constants
import io.github.diskria.utils.kotlin.extensions.ensurePrefix
import io.github.diskria.utils.kotlin.extensions.generics.toFlatString
import io.github.diskria.utils.kotlin.extensions.primitives.toUnsignedLong
import io.ktor.http.*
import java.util.*

infix fun <A, B, C> Pair<A, B>.to(third: C): Triple<A, B, C> =
    Triple(first, second, third)

inline fun <T> tryCatch(catchErrors: Boolean = true, block: () -> T): Boolean =
    tryCatchOrNull(catchErrors, block) != null

inline fun <T : Any> tryCatchOr(fallback: T, catchErrors: Boolean = true, block: () -> T?): T =
    tryCatchOrNull(catchErrors, block) ?: fallback

inline fun <T : Any> tryCatchOrElse(catchErrors: Boolean = true, fallback: () -> T, block: () -> T?): T =
    tryCatchOrNull(catchErrors, block) ?: fallback()

inline fun <T> tryCatchOrNull(catchErrors: Boolean = true, block: () -> T?): T? =
    try {
        block()
    } catch (error: Throwable) {
        if (catchErrors || error is Exception) null
        else throw error
    }

fun packIntsToLong(highInt: Int, lowInt: Int): Long =
    highInt.toUnsignedLong().shl(Int.SIZE_BITS).or(lowInt.toUnsignedLong())

fun generateUUID(): String =
    UUID.randomUUID().toString()

fun fileName(name: String, vararg extensions: String): String =
    name + extensions.asIterable().toFlatString { it.ensurePrefix(Constants.Char.DOT) }

fun buildString(vararg parts: Any?): String =
    buildString {
        append(*parts)
    }

fun buildEmail(localPart: String, domain: String): String =
    buildString(localPart, Constants.Char.AT, domain)

fun buildUrl(
    host: String,
    protocol: URLProtocol = URLProtocol.HTTPS,
    builder: URLBuilder.() -> Unit = {},
): String =
    URLBuilder().apply {
        this.protocol = protocol
        this.host = host
        builder()
    }.buildString()

fun KotlinClass<*>.primitiveTypeNameOrNull(): String? =
    javaPrimitiveType?.name

fun nowDate(): Date =
    Date()

fun nowMillis(): Long =
    System.currentTimeMillis()

fun combineChars(vararg chars: Char): String =
    chars.singleOrNull()?.toString() ?: chars.asIterable().toFlatString()
