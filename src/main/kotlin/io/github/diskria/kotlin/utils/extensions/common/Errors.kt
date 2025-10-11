package io.github.diskria.kotlin.utils.extensions.common

import io.github.diskria.kotlin.utils.Constants
import io.github.diskria.kotlin.utils.extensions.primitives.wrapWithSpace
import io.github.diskria.kotlin.utils.extensions.setCase
import io.github.diskria.kotlin.utils.extensions.toNullIfEmpty
import io.github.diskria.kotlin.utils.extensions.wrapWithSingleQuote
import io.github.diskria.kotlin.utils.poet.Property
import io.github.diskria.kotlin.utils.properties.autoNamedProperty

fun failWithWrongUsage(useInsteadThis: String): Nothing =
    failWithDetails("Wrong usage detected") {
        val useInsteadThis by useInsteadThis.autoNamedProperty()
        listOf(useInsteadThis)
    }

fun failWithUnsupportedType(clazz: KotlinClass<*>): Nothing =
    failWithDetails("Unsupported type") {
        val className by clazz.className().autoNamedProperty()
        listOf(className)
    }

fun failWithInvalidValue(value: Any?): Nothing =
    failWithDetails("Invalid value") {
        val value by value.autoNamedProperty()
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
                            append(property.name.setCase(camelCase, `Sentence case`))
                            append(Constants.Char.COLON)
                            append(Constants.Char.SPACE)
                            append(property.value.toString().wrapWithSingleQuote())
                        }
                    )
                }
            }
        }
    )
