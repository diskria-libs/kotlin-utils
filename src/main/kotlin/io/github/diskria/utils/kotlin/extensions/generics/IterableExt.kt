package io.github.diskria.utils.kotlin.extensions.generics

import io.github.diskria.utils.kotlin.Constants
import io.github.diskria.utils.kotlin.extensions.common.failWithUnsupportedType
import io.github.diskria.utils.kotlin.extensions.primitives.nonNegativeOrNull
import io.github.diskria.utils.kotlin.extensions.primitives.repeat

inline fun <T, R : Any> Iterable<T>.flatMapNotNull(
    transform: (T) -> Iterable<R?>?,
): List<R> =
    mapNotNull(transform).flatMap { it }.filterNotNull()

fun <T> Iterable<T>.joinBySpace(transform: ((T) -> CharSequence)? = null): String =
    joinToString(separator = Constants.Char.SPACE, transform = transform)

fun <T> Iterable<T>.joinByNewLine(
    linesCount: Int = 1,
    transform: ((T) -> CharSequence)? = null,
): String =
    joinToString(separator = Constants.Char.NEW_LINE.repeat(linesCount), transform = transform)

inline fun <A, B> Iterable<A>.mapToPairs(transform: (A) -> B): List<Pair<A, B>> =
    map { first ->
        first to transform(first)
    }

inline fun <A, B> Iterable<A>.mapToPairsNotNull(transform: (A) -> B?): List<Pair<A, B>> =
    mapNotNull { first ->
        transform(first)?.let { second ->
            first to second
        }
    }

fun <T> Iterable<T>.toFlatString(transform: ((T) -> CharSequence)? = null): String =
    joinToString(separator = Constants.Char.EMPTY, transform = transform)

fun <T> Iterable<T>.joinToString(
    separator: Char,
    transform: ((T) -> CharSequence)? = null,
): String =
    joinToString(separator.toString(), transform = transform)

fun <T> Iterable<T>?.isNullOrEmpty(): Boolean =
    this == null || !this.iterator().hasNext()

fun Iterable<String>.containsIgnoreCase(value: String): Boolean =
    any { it.equals(value, ignoreCase = true) }

inline fun <T, R> Iterable<T>.foldChain(initial: R, crossinline operation: R.(T) -> R): R =
    fold(initial) { accumulatorValue, element -> accumulatorValue.operation(element) }

inline fun <T, reified R> Iterable<T>.foldChain(crossinline operation: R.(T) -> R): R =
    foldChain(
        when (val clazz = R::class) {
            Int::class -> 0
            Long::class -> 0.toLong()
            Double::class -> 0.toDouble()
            Float::class -> 0.toFloat()
            Boolean::class -> false
            String::class -> Constants.Char.EMPTY
            else -> failWithUnsupportedType(clazz)
        } as R,
        operation
    )

fun <T> Iterable<T>.indexOfFirstOrNull(predicate: (T) -> Boolean): Int? =
    indexOfFirst { predicate(it) }.nonNegativeOrNull()

inline fun <T> List<T>.forEachWindow(
    crossinline action: (prev: () -> T?, current: T, next: () -> T?) -> Unit
) {
    forEachIndexed { index, element ->
        var previousElement: T? = null
        var nextElement: T? = null
        var isPreviousComputed = false
        var isNextComputed = false

        action(
            {
                if (!isPreviousComputed) {
                    previousElement = getOrNull(index.dec())
                    isPreviousComputed = true
                }
                previousElement
            },
            element,
            {
                if (!isNextComputed) {
                    nextElement = getOrNull(index.inc())
                    isNextComputed = true
                }
                nextElement
            }
        )
    }
}

inline fun <T, R> List<T>.foldChainWindow(
    initial: R,
    crossinline action: R.(previous: () -> T?, current: T, next: () -> T?) -> Unit
): R =
    withIndex().foldChain(initial) { (index, element) ->
        action(
            { getOrNull(index.dec()) },
            element,
            { getOrNull(index.inc()) }
        )
        this
    }
