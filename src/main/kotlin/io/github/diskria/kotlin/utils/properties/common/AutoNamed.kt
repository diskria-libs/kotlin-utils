package io.github.diskria.kotlin.utils.properties.common

import io.github.diskria.kotlin.utils.properties.AutoNamedEnvironmentVariable

object AutoNamed

val autoNamed = AutoNamed

fun AutoNamed.environmentVariable(isRequired: Boolean = false): AutoNamedEnvironmentVariable =
    AutoNamedEnvironmentVariable(isRequired)
