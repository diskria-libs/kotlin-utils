package io.github.diskria.utils.kotlin.properties

import io.github.diskria.utils.kotlin.extensions.common.SCREAMING_SNAKE_CASE
import io.github.diskria.utils.kotlin.extensions.common.camelCase
import io.github.diskria.utils.kotlin.extensions.setCase
import io.github.diskria.utils.kotlin.extensions.toNullIfEmpty
import io.github.diskria.utils.kotlin.properties.common.AbstractAutoNamedProperty

class AutoNamedEnvironmentVariable : AbstractAutoNamedProperty<String?>() {

    override fun mapToValue(propertyName: String): String? =
        System.getenv(propertyName)
            ?.toNullIfEmpty()
            ?.setCase(camelCase, SCREAMING_SNAKE_CASE)
}
