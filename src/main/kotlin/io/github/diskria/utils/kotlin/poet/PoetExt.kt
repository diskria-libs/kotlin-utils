package io.github.diskria.utils.kotlin.poet

import com.squareup.kotlinpoet.*
import io.github.diskria.utils.kotlin.Constants
import io.github.diskria.utils.kotlin.properties.toAutoNamedProperty
import io.github.diskria.utils.kotlin.extensions.common.KotlinModifier
import io.github.diskria.utils.kotlin.extensions.common.failWithDetails

inline fun <reified T> poetProperty(property: Property<T>, isConstant: Boolean = false): PropertySpec {
    val name = property.name
    val value = property.value

    val clazz = T::class
    val type = when (clazz) {
        Boolean::class -> BOOLEAN
        Int::class -> INT
        Long::class -> LONG
        Float::class -> FLOAT
        Double::class -> DOUBLE
        Char::class -> CHAR
        String::class -> STRING
        else -> when {
            clazz.java.isEnum -> STRING
            else -> null
        }
    }
    val format = Constants.Char.PERCENT_SIGN + when (type) {
        BOOLEAN, INT, LONG, DOUBLE, CHAR -> "L"
        FLOAT -> "Lf"
        STRING -> "S"
        else -> {
            val name by name.toAutoNamedProperty()
            val type by type.toAutoNamedProperty()
            val value by value.toAutoNamedProperty()
            val clazz by clazz.toAutoNamedProperty()
            failWithDetails("Unsupported property value type", name, type, value, clazz)
        }
    }
    return buildProperty(name, type) {
        if (isConstant) {
            addModifiers(KotlinModifier.CONST)
        }
        initializer(format, value)
    }
}

fun poetObject(name: String, build: TypeSpec.Builder.() -> Unit): TypeSpec =
    TypeSpec.objectBuilder(name).apply(build).build()

fun poetFile(packageName: String, fileName: String, build: FileSpec.Builder.() -> Unit): FileSpec =
    FileSpec.builder(packageName, fileName).apply(build).build()

fun TypeSpec.poetFile(packageName: String, fileName: String): FileSpec =
    FileSpec.builder(packageName, fileName).addType(this).build()

fun buildProperty(name: String, type: ClassName, builder: PropertySpec.Builder.() -> Unit): PropertySpec =
    PropertySpec.builder(name, type).apply(builder).build()
