package io.github.diskria.kotlin.utils.properties

import io.github.diskria.kotlin.utils.extensions.common.camelCase
import io.github.diskria.kotlin.utils.extensions.setCase
import io.github.diskria.kotlin.utils.poet.Property
import io.github.diskria.kotlin.utils.properties.common.AbstractAutoNamedProperty
import io.github.diskria.kotlin.utils.words.StringCase

class AutoNamedProperty<T>(val value: T, val case: StringCase) : AbstractAutoNamedProperty<Property<T>>() {

    override fun mapToValue(propertyName: String): Property<T> =
        Property(propertyName.setCase(camelCase, case), value)
}

inline fun <reified T> T.toAutoNamedProperty(case: StringCase = camelCase): AutoNamedProperty<T> =
    AutoNamedProperty(this, case)
