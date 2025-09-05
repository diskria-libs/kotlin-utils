package io.github.diskria.utils.kotlin.extensions.common

fun KotlinClass<*>.className(): String =
    simpleName ?: failWithUnsupportedType(this)

fun <T : Any> KotlinClass<T>.sealedSubclassesRecursive(): List<KotlinClass<out T>> =
    flattenTree<KotlinClass<out T>> { it.sealedSubclasses }

fun <T : Any> KotlinClass<T>.sealedObjectsRecursive(): List<T> =
    sealedSubclassesRecursive().mapNotNull { it.objectInstance }

inline fun <T> T.modify(block: (T) -> T): T =
    map(block)

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

inline fun <T, R> T.map(block: (T) -> R): R =
    block(this)

inline fun <T, R> T.mapIf(condition: Boolean, elseValue: R, block: (T) -> R): R =
    if (condition) map(block)
    else elseValue

inline fun <T, R> T.mapUnless(condition: Boolean, elseValue: R, block: (T) -> R): R =
    mapIf(!condition, elseValue, block)

inline fun <T, R> T.mapIfAnyOf(vararg candidates: T, elseValue: R, block: (T) -> R): R =
    mapIf(isAnyOf(*candidates), elseValue, block)

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
