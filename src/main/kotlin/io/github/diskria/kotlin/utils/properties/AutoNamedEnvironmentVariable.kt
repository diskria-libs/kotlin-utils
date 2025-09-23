package io.github.diskria.kotlin.utils.properties

import io.github.diskria.kotlin.utils.extensions.common.SCREAMING_SNAKE_CASE
import io.github.diskria.kotlin.utils.extensions.common.camelCase
import io.github.diskria.kotlin.utils.extensions.setCase
import io.github.diskria.kotlin.utils.extensions.toNullIfEmpty
import io.github.diskria.kotlin.utils.properties.common.AbstractAutoNamedProperty

class AutoNamedEnvironmentVariable : AbstractAutoNamedProperty<String?>() {

    override fun mapToValue(propertyName: String): String? =
        System.getenv(propertyName)
            ?.toNullIfEmpty()
            ?.setCase(camelCase, SCREAMING_SNAKE_CASE)
}
