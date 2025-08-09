package io.github.diskria.utils.kotlin.extensions.common

import io.github.diskria.utils.kotlin.Constants
import io.github.diskria.utils.kotlin.delegates.toAutoNamedPair
import io.github.diskria.utils.kotlin.extensions.primitives.wrapWithSpace
import io.github.diskria.utils.kotlin.extensions.wrapWithSingleQuote

fun failAsImpossibleOperation(): Nothing =
    error("This code path should be impossible. If you're seeing this, something is broken.")

fun unsupportedOperation(): Nothing =
    error("Unsupported operation")

fun initializeFirst(): Nothing =
    error("Not initialized")

fun failWithWrongUsage(useInsteadThis: String): Nothing =
    error("Use $useInsteadThis instead this")

fun failWithUnsupportedType(clazz: KotlinClass<*>): Nothing =
    failWithDetails("Unsupported type") {
        val className by clazz.qualifiedName.toAutoNamedPair()
        listOf(className)
    }

fun failWithInvalidValue(value: Any?): Nothing =
    failWithDetails("Invalid value") {
        val value by value.toAutoNamedPair()
        listOf(value)
    }

fun failWithDetails(description: String? = null, values: () -> List<Pair<String, Any?>>): Nothing =
    failWithDetails(description, *values().toTypedArray())

fun failWithDetails(description: String? = null, values: List<Pair<String, Any?>>): Nothing =
    failWithDetails(description, *values.toTypedArray())

fun failWithDetails(vararg values: Pair<String, Any?>): Nothing =
    failWithDetails(description = null, values = values)

fun failWithDetails(description: String? = null, vararg values: Pair<String, Any?>): Nothing =
    error(
        buildString {
            appendLine(description ?: "Operation failed")
            if (values.isNotEmpty()) {
                appendLine("Details:")
                values.forEach { (key, value) ->
                    appendLine(
                        buildString {
                            append(Constants.Char.HYPHEN.wrapWithSpace())
                            append(key.wrapWithSingleQuote())
                            append(Constants.Char.EQUAL_SIGN.wrapWithSpace())
                            append(value.toString().wrapWithSingleQuote())
                        }
                    )
                }
            } else {
                appendLine("No additional details were provided.")
            }
        }
    )
