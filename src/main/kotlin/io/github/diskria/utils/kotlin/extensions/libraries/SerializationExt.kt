package io.github.diskria.utils.kotlin.extensions.libraries

import io.github.diskria.utils.kotlin.annotations.IgnoreUnknownKeys
import io.github.diskria.utils.kotlin.extensions.common.KotlinClass
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.full.findAnnotation

val serializationClassToJsonCache: MutableMap<KotlinClass<*>, Json> = ConcurrentHashMap()

inline fun <reified T> jsonFor(): Json =
    serializationClassToJsonCache.getOrPut(T::class) {
        Json {
            if (T::class.shouldIgnoreUnknownKeys()) {
                ignoreUnknownKeys = true
            }
        }
    }

fun KotlinClass<*>.shouldIgnoreUnknownKeys(): Boolean =
    findAnnotation<IgnoreUnknownKeys>() != null
