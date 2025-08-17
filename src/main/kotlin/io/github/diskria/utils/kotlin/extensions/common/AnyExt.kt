package io.github.diskria.utils.kotlin.extensions.common

fun KotlinClass<*>.className(): String =
    simpleName ?: failWithUnsupportedType(this)

inline fun <T, R> T.map(block: (T) -> R): R =
    block(this)

inline fun <T> T.modify(block: (T) -> T): T =
    map(block)

inline fun <T, R> T.mapIf(condition: Boolean, elseValue: R, block: (T) -> R): R =
    if (condition) map(block)
    else elseValue

inline fun <T> T.modifyIf(condition: Boolean, block: (T) -> T): T =
    if (condition) modify(block)
    else this

inline fun <T, R> T.mapUnless(condition: Boolean, elseValue: R, block: (T) -> R): R =
    mapIf(!condition, elseValue, block)

inline fun <T> T.modifyUnless(condition: Boolean, block: (T) -> T): T =
    modifyIf(!condition, block)

fun <T> T.isAnyOf(vararg candidates: T): Boolean =
    candidates.any { this == it }

fun <T> T.isNoneOf(vararg candidates: T): Boolean =
    candidates.all { this != it }

inline fun <T, R> T.mapIfAnyOf(vararg candidates: T, elseValue: R, block: (T) -> R): R =
    mapIf(isAnyOf(*candidates), elseValue, block)
