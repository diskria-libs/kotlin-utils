package io.github.diskria.kotlin.utils.extensions.common

import kotlin.reflect.KClass

fun KClass<*>.className(): String =
    simpleName ?: failWithUnsupportedType(this)

fun <T : Any> KClass<T>.sealedSubclassesRecursive(): List<KClass<out T>> =
    flattenTree<KClass<out T>> { it.sealedSubclasses }

fun <T : Any> KClass<T>.sealedObjectsRecursive(): List<T> =
    sealedSubclassesRecursive().mapNotNull { it.objectInstance }

inline fun <T> T.modify(block: (T) -> T): T =
    remap(block)

inline fun <T> T.modifyIf(condition: Boolean, block: (T) -> T): T =
    if (condition) modify(block)
    else this

inline fun <T> T.modifyUnless(condition: Boolean, block: (T) -> T): T =
    modifyIf(!condition, block)

inline fun <T> T.flattenTree(crossinline children: (T) -> Iterable<T>): List<T> {
    val result = mutableListOf<T>()
    val stack = ArrayDeque(children(this).toList())
    while (stack.isNotEmpty()) {
        val current = stack.removeLast()
        result.add(current)
        stack.addAll(children(current))
    }
    return result
}

fun <T> T.isAnyOf(vararg candidates: T): Boolean =
    candidates.any { this == it }

fun <T> T.isNoneOf(vararg candidates: T): Boolean =
    candidates.all { this != it }

inline fun <T, R> T.remap(block: (T) -> R): R =
    block(this)

inline fun <T, R> T.remapIf(condition: Boolean, elseValue: R, block: (T) -> R): R =
    if (condition) remap(block)
    else elseValue

inline fun <T, R> T.remapUnless(condition: Boolean, elseValue: R, block: (T) -> R): R =
    remapIf(!condition, elseValue, block)

inline fun <T, R> T.remapIfAnyOf(vararg candidates: T, elseValue: R, block: (T) -> R): R =
    remapIf(isAnyOf(*candidates), elseValue, block)
