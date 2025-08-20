package io.github.diskria.utils.kotlin.delegates

import io.github.diskria.utils.kotlin.extensions.setCase
import io.github.diskria.utils.kotlin.poet.Property
import io.github.diskria.utils.kotlin.words.CamelCase
import io.github.diskria.utils.kotlin.words.StringCase

class AutoNamedPairProperty<T>(value: T, case: StringCase) : AbstractAutoNamedProperty<Property<T>>(
    { propertyName -> Property(propertyName.setCase(CamelCase, case), value) }
)

inline fun <reified T> T.toAutoNamedProperty(case: StringCase = CamelCase): AutoNamedPairProperty<T> =
    AutoNamedPairProperty(this, case)
