package io.github.diskria.kotlin.utils.extensions.common

import io.github.diskria.kotlin.utils.Constants
import io.github.diskria.kotlin.utils.extensions.ensurePrefix
import io.github.diskria.kotlin.utils.extensions.generics.toFlatString
import io.github.diskria.kotlin.utils.extensions.primitives.toUnsignedLong
import io.github.diskria.kotlin.utils.helpers.EmailType
import io.ktor.http.*
import java.util.*

infix fun <A, B, C> Pair<A, B>.to(third: C): Triple<A, B, C> =
    Triple(first, second, third)

inline fun <T> tryOrNull(suppressErrors: Boolean = true, block: () -> T?): T? =
    try {
        block()
    } catch (error: Throwable) {
        if (suppressErrors || error is Exception) null
        else throw error
    }

inline fun <T> tryCatch(catchErrors: Boolean = true, block: () -> T): Boolean =
    tryOrNull(catchErrors, block) != null

inline fun <T : Any> tryOr(fallback: T, catchErrors: Boolean = true, block: () -> T?): T =
    tryOrNull(catchErrors, block) ?: fallback

fun packIntsToLong(highInt: Int, lowInt: Int): Long =
    highInt.toUnsignedLong().shl(Int.SIZE_BITS).or(lowInt.toUnsignedLong())

fun generateUUID(): String =
    UUID.randomUUID().toString()

fun fileName(name: String, vararg extensions: String): String =
    name + extensions.asIterable().toFlatString { it.ensurePrefix(Constants.Char.DOT) }

fun emptyFileName(vararg extensions: String): String =
    fileName(Constants.Char.EMPTY, *extensions)

fun buildString(vararg parts: Any?): String =
    buildString {
        append(*parts)
    }

fun buildEmail(localPart: String, domain: String): String =
    buildString(localPart, Constants.Char.AT_SIGN, domain)

fun buildEmail(localPart: String, type: EmailType): String =
    buildEmail(localPart, type.domain)

fun buildUrl(builder: URLBuilder.() -> Unit): Url =
    URLBuilder().apply(builder).build()

fun buildUrl(host: String, protocol: URLProtocol = URLProtocol.HTTPS, builder: URLBuilder.() -> Unit = {}): Url =
    buildUrl {
        this.host = host
        protocol.let { this.protocol = it }
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
