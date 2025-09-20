package io.github.diskria.utils.kotlin.extensions.mappers

import io.github.diskria.utils.kotlin.extensions.common.failWithDetails
import io.github.diskria.utils.kotlin.extensions.common.snake_case
import io.github.diskria.utils.kotlin.extensions.equalsIgnoreCase
import io.github.diskria.utils.kotlin.extensions.setCase
import io.github.diskria.utils.kotlin.properties.toAutoNamedProperty
import io.github.diskria.utils.kotlin.words.StringCase
import kotlin.enums.enumEntries

inline fun <reified T : Enum<T>> String.toEnumOrNull(): T? =
    enumEntries<T>().firstOrNull { it.name.equalsIgnoreCase(this) }

inline fun <reified T : Enum<T>> String.toEnum(): T =
    toEnumOrNull<T>() ?: failWithDetails("Unknown enum name") {
        val enumName by this.toAutoNamedProperty()
        val availableValues by enumEntries<T>().toAutoNamedProperty()
        listOf(enumName, availableValues)
    }

fun <E : Enum<E>> E.toName(case: StringCase? = null): String =
    name.lowercase().run {
        if (case != null) setCase(snake_case, case)
        else this
    }
