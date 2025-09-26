package io.github.diskria.kotlin.utils.extensions.serialization

import io.github.diskria.kotlin.utils.extensions.common.KotlinClass
import io.github.diskria.kotlin.utils.serialization.annotations.IgnoreUnknownKeys
import io.github.diskria.kotlin.utils.serialization.annotations.PrettyPrint
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.full.findAnnotation

val jsonCache: MutableMap<KotlinClass<*>, Json> by lazy { ConcurrentHashMap() }

inline fun <reified T> jsonFor(): Json =
    jsonCache.getOrPut(T::class) {
        Json {
            if (T::class.shouldEnablePrettyPrint()) {
                prettyPrint = true
            }
            if (T::class.shouldIgnoreUnknownKeys()) {
                ignoreUnknownKeys = true
            }
        }
    }

fun KotlinClass<*>.shouldEnablePrettyPrint(): Boolean =
    findAnnotation<PrettyPrint>() != null

fun KotlinClass<*>.shouldIgnoreUnknownKeys(): Boolean =
    findAnnotation<IgnoreUnknownKeys>() != null

inline fun <reified T> T.serialize(file: File) {
    file.writeText(jsonFor<T>().encodeToString(this))
}

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> File.deserialize(): T =
    jsonFor<T>().decodeFromStream(inputStream())
