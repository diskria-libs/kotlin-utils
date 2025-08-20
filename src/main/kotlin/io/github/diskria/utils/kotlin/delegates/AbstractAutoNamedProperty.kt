package io.github.diskria.utils.kotlin.delegates

import io.github.diskria.utils.kotlin.extensions.common.KotlinProperty

abstract class AbstractAutoNamedProperty<T>(val mapToValue: (propertyName: String) -> T) {
    operator fun getValue(reference: Any?, property: KotlinProperty<*>): T = mapToValue(property.name)
}
