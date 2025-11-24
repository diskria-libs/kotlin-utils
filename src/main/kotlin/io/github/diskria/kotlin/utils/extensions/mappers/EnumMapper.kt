package io.github.diskria.kotlin.utils.extensions.mappers

import io.github.diskria.kotlin.utils.extensions.common.SCREAMING_SNAKE_CASE
import io.github.diskria.kotlin.utils.extensions.common.failWithDetails
import io.github.diskria.kotlin.utils.extensions.common.snake_case
import io.github.diskria.kotlin.utils.extensions.equalsIgnoreCase
import io.github.diskria.kotlin.utils.extensions.setCase
import io.github.diskria.kotlin.utils.properties.autoNamedProperty
import io.github.diskria.kotlin.utils.words.StringCase
import kotlin.enums.enumEntries

inline fun <reified T : Enum<T>> String.toEnumOrNull(case: StringCase = snake_case): T? =
    enumEntries<T>().firstOrNull { it.getName(case) == this }

inline fun <reified T : Enum<T>> String.toEnum(case: StringCase = snake_case): T =
    toEnumOrNull<T>(case) ?: failWithDetails("Unknown enum name") {
        val enumName by this.autoNamedProperty()
        val availableValues by enumEntries<T>().autoNamedProperty()
        listOf(enumName, availableValues)
    }

fun <E : Enum<E>> E.getName(case: StringCase = snake_case): String =
    name.setCase(SCREAMING_SNAKE_CASE, case)
