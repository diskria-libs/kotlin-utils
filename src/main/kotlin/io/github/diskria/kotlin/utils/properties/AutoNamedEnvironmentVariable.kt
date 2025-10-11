package io.github.diskria.kotlin.utils.properties

import io.github.diskria.kotlin.utils.extensions.common.SCREAMING_SNAKE_CASE
import io.github.diskria.kotlin.utils.extensions.common.camelCase
import io.github.diskria.kotlin.utils.extensions.setCase
import io.github.diskria.kotlin.utils.properties.common.AbstractAutoNamedProperty

class AutoNamedEnvironmentVariable(val isRequired: Boolean) : AbstractAutoNamedProperty<String>() {

    override fun mapToValue(propertyName: String): String {
        val variableName = propertyName.setCase(camelCase, SCREAMING_SNAKE_CASE)
        val value = System.getenv(variableName).orEmpty().trim()
        if (isRequired && value.isEmpty()) {
            error("Environment variable $variableName must be set!")
        }
        return value
    }
}
