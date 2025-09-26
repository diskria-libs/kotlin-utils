package io.github.diskria.kotlin.utils.extensions.mappers

import io.github.diskria.kotlin.utils.extensions.common.SCREAMING_SNAKE_CASE
import io.github.diskria.kotlin.utils.extensions.common.failWithDetails
import io.github.diskria.kotlin.utils.extensions.common.snake_case
import io.github.diskria.kotlin.utils.extensions.equalsIgnoreCase
import io.github.diskria.kotlin.utils.extensions.setCase
import io.github.diskria.kotlin.utils.properties.toAutoNamedProperty
import io.github.diskria.kotlin.utils.words.StringCase
import kotlin.enums.enumEntries

inline fun <reified T : Enum<T>> String.toEnumOrNull(): T? =
    enumEntries<T>().firstOrNull { it.name.equalsIgnoreCase(this) }

inline fun <reified T : Enum<T>> String.toEnum(): T =
    toEnumOrNull<T>() ?: failWithDetails("Unknown enum name") {
        val enumName by this.toAutoNamedProperty()
        val availableValues by enumEntries<T>().toAutoNamedProperty()
        listOf(enumName, availableValues)
    }

fun <E : Enum<E>> E.getName(case: StringCase = snake_case): String =
    name.setCase(SCREAMING_SNAKE_CASE, case)
