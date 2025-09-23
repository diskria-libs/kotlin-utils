package io.github.diskria.kotlin.utils.properties.common

import io.github.diskria.kotlin.utils.extensions.common.KotlinProperty
import kotlin.properties.ReadOnlyProperty

abstract class AbstractAutoNamedProperty<T> : ReadOnlyProperty<Any?, T> {

    abstract fun mapToValue(propertyName: String): T

    override fun getValue(thisRef: Any?, property: KotlinProperty<*>): T =
        mapToValue(property.name)
}
