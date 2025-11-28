package io.github.diskria.kotlin.utils.properties.common

import io.github.diskria.kotlin.utils.extensions.common.KotlinProperty
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class AbstractAutoNamedProperty<T> : ReadOnlyProperty<Any?, T> {

    protected var mappedValue: T? = null

    abstract fun mapToValue(propertyName: String): T

    override fun getValue(thisRef: Any?, property: KotlinProperty<*>): T =
        mappedValue ?: mapToValue(property.name).also { mappedValue = it }

    open operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): ReadOnlyProperty<Any?, T> {
        val value = mapToValue(property.name)
        mappedValue = value
        return ReadOnlyProperty { _, _ -> value }
    }
}
