package io.github.diskria.kotlin.utils.extensions.common

import io.github.diskria.kotlin.utils.Constants
import io.github.diskria.kotlin.utils.extensions.ensurePrefix
import io.github.diskria.kotlin.utils.extensions.generics.toFlatString
import io.github.diskria.kotlin.utils.extensions.primitives.toUnsignedLong
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
    buildString(localPart, Constants.Char.AT_SIGN, domain)

fun buildUrl(builder: URLBuilder.() -> Unit): String =
    URLBuilder().apply(builder).buildString()

fun buildUrl(
    host: String? = null,
    protocol: URLProtocol = URLProtocol.HTTPS,
    builder: URLBuilder.() -> Unit = {},
): String =
    buildUrl {
        protocol.let { this.protocol = it }
        host?.let { this.host = it }
        builder()
    }

fun KotlinClass<*>.primitiveTypeNameOrNull(): String? =
    javaPrimitiveType?.name

fun nowDate(): Date =
    Date()

fun nowMillis(): Long =
    System.currentTimeMillis()

fun combineChars(vararg chars: Char): String =
    chars.singleOrNull()?.toString() ?: chars.asIterable().toFlatString()
