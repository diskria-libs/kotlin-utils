package io.github.diskria.utils.kotlin.libraries

import com.squareup.kotlinpoet.*
import io.github.diskria.utils.kotlin.delegates.toAutoNamedPair
import io.github.diskria.utils.kotlin.extensions.common.failWithDetails

inline fun <reified T> poetProperty(
    name: String,
    value: T,
    isConst: Boolean = false,
): PropertySpec {
    val (type, format) = when (val clazz = T::class) {
        Boolean::class -> BOOLEAN to "%L"
        Int::class -> INT to "%L"
        Long::class -> LONG to "%L"
        Float::class -> FLOAT to "%Lf"
        Double::class -> DOUBLE to "%L"
        Char::class -> CHAR to "%L"
        String::class -> STRING to "%S"
        is Enum<*> -> STRING to "%S"
        else -> {
            val name by name.toAutoNamedPair()
            val value by value.toAutoNamedPair()
            val className by clazz.qualifiedName.toAutoNamedPair()
            failWithDetails("Unsupported property value type", name, value, className)
        }
    }
    return PropertySpec.builder(name, type).apply {
        if (isConst) {
            addModifiers(KModifier.CONST)
        }
        initializer(format, value)
    }.build()
}

fun poetObject(
    name: String,
    build: TypeSpec.Builder.() -> Unit
): TypeSpec =
    TypeSpec.objectBuilder(name).apply(build).build()

fun poetFile(
    packageName: String,
    fileName: String,
    build: FileSpec.Builder.() -> Unit
): FileSpec =
    FileSpec.builder(packageName, fileName).apply(build).build()

fun TypeSpec.poetFile(packageName: String, fileName: String): FileSpec =
    FileSpec.builder(packageName, fileName).addType(this).build()
