package io.github.diskria.utils.kotlin.extensions.common

fun KotlinClass<*>.className(): String =
    simpleName ?: failWithUnsupportedType(this)

inline fun <T> T.modify(block: (T) -> T): T =
    block(this)

inline fun <T> T.modifyIf(condition: Boolean, block: (T) -> T): T =
    if (condition) modify(block)
    else this

inline fun <T> T.modifyUnless(condition: Boolean, block: (T) -> T): T =
    modifyIf(!condition, block)
