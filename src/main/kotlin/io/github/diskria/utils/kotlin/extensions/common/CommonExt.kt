package io.github.diskria.utils.kotlin.extensions.common

import io.github.diskria.utils.kotlin.Constants
import io.github.diskria.utils.kotlin.extensions.ensurePrefix
import io.github.diskria.utils.kotlin.extensions.generics.toFlatString
import io.github.diskria.utils.kotlin.extensions.primitives.toUnsignedLong
import java.util.*

infix fun <A, B, C> Pair<A, B>.to(third: C): Triple<A, B, C> =
    Triple(first, second, third)

inline fun <T> tryCatch(catchErrors: Boolean = true, block: () -> T): Boolean =
    tryCatchOrNull(catchErrors, block) != null

inline fun <T : Any> tryCatchOr(fallback: T, catchErrors: Boolean = true, block: () -> T?): T =
    tryCatchOrNull(catchErrors, block) ?: fallback

inline fun <T : Any> tryCatchOrElse(
    catchErrors: Boolean = true,
    fallback: () -> T,
    block: () -> T?
): T =
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

fun generateUuid(): String =
    UUID.randomUUID().toString()

fun fileName(name: String, vararg extensions: String): String =
    name + extensions.asIterable().toFlatString { it.ensurePrefix(Constants.Char.DOT) }

fun KotlinClass<*>.primitiveTypeNameOrNull(): String? =
    javaPrimitiveType?.name

fun Any.toPrimitiveClassOrNull(): KotlinClass<*>? =
    when (this) {
        is Boolean -> Boolean::class
        is Int -> Int::class
        is Long -> Long::class
        is Float -> Float::class
        is Double -> Double::class
        is Char -> Char::class
        else -> null
    }

fun Any.primitiveTypeNameOrNull(): String? =
    toPrimitiveClassOrNull()?.primitiveTypeNameOrNull()

fun nowDate(): Date =
    Date()

fun nowMillis(): Long =
    System.currentTimeMillis()

fun combineChars(vararg chars: Char): String =
    chars.singleOrNull()?.toString() ?: chars.asIterable().toFlatString()
