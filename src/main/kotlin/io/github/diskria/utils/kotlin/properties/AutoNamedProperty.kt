package io.github.diskria.utils.kotlin.properties

import io.github.diskria.utils.kotlin.extensions.common.camelCase
import io.github.diskria.utils.kotlin.extensions.setCase
import io.github.diskria.utils.kotlin.poet.Property
import io.github.diskria.utils.kotlin.properties.common.AbstractAutoNamedProperty
import io.github.diskria.utils.kotlin.words.CamelCase
import io.github.diskria.utils.kotlin.words.StringCase

class AutoNamedProperty<T>(val value: T, val case: StringCase) : AbstractAutoNamedProperty<Property<T>>() {

    override fun mapToValue(propertyName: String): Property<T> =
        Property(propertyName.setCase(CamelCase, case), value)
}

inline fun <reified T> T.toAutoNamedProperty(case: StringCase = camelCase): AutoNamedProperty<T> =
    AutoNamedProperty(this, case)
