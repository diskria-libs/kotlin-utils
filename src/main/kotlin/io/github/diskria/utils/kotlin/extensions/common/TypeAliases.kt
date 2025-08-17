package io.github.diskria.utils.kotlin.extensions.common

import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

typealias KotlinClass<T> = KClass<T>
typealias KotlinProperty<T> = KProperty<T>
typealias KotlinSerializer<T> = KSerializer<T>
