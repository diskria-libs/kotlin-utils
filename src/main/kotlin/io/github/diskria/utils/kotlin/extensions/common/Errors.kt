package io.github.diskria.utils.kotlin.extensions.common

import io.github.diskria.utils.kotlin.Constants
import io.github.diskria.utils.kotlin.delegates.toAutoNamedProperty
import io.github.diskria.utils.kotlin.extensions.primitives.wrapWithSpace
import io.github.diskria.utils.kotlin.extensions.setCase
import io.github.diskria.utils.kotlin.extensions.toNullIfEmpty
import io.github.diskria.utils.kotlin.extensions.wrapWithSingleQuote
import io.github.diskria.utils.kotlin.poet.Property

fun failWithWrongUsage(useInsteadThis: String): Nothing =
    failWithDetails("Wrong usage detected") {
        val useInsteadThis by useInsteadThis.toAutoNamedProperty()
        listOf(useInsteadThis)
    }

fun failWithUnsupportedType(clazz: KotlinClass<*>): Nothing =
    failWithDetails("Unsupported type") {
        val className by clazz.className().toAutoNamedProperty()
        listOf(className)
    }

fun failWithInvalidValue(value: Any?): Nothing =
    failWithDetails("Invalid value") {
        val value by value.toAutoNamedProperty()
        listOf(value)
    }

fun failWithDetails(description: String? = null, values: () -> List<Property<Any?>>): Nothing =
    failWithDetails(description, *values().toTypedArray())

fun failWithDetails(description: String? = null, values: List<Property<Any?>>): Nothing =
    failWithDetails(description, *values.toTypedArray())

fun failWithDetails(vararg values: Property<Any?>): Nothing =
    failWithDetails(description = null, values = values)

fun failWithDetails(description: String? = null, vararg values: Property<Any?>): Nothing =
    error(
        buildString {
            appendLine(description.toNullIfEmpty() ?: "Operation failed")
            if (values.isNotEmpty()) {
                appendLine("Details:")
                values.forEach { property ->
                    appendLine(
                        buildString {
                            append(Constants.Char.HYPHEN.wrapWithSpace())
                            append(property.name.setCase(camelCase, `space case`))
                            append(Constants.Char.COLON)
                            append(Constants.Char.SPACE)
                            append(property.value.toString().wrapWithSingleQuote())
                        }
                    )
                }
            }
        }
    )
