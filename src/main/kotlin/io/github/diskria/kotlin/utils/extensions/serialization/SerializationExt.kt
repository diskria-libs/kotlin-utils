package io.github.diskria.kotlin.utils.extensions.serialization

import io.github.diskria.kotlin.utils.extensions.common.KotlinClass
import io.github.diskria.kotlin.utils.serialization.annotations.EncodeDefaults
import io.github.diskria.kotlin.utils.serialization.annotations.ExplicitNulls
import io.github.diskria.kotlin.utils.serialization.annotations.IgnoreUnknownKeys
import io.github.diskria.kotlin.utils.serialization.annotations.PrettyPrint
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.full.hasAnnotation

val jsonCache: MutableMap<KotlinClass<*>, Json> by lazy { ConcurrentHashMap() }

inline fun <reified T> jsonFor(): Json =
    jsonCache.getOrPut(T::class) {
        Json {
            ignoreUnknownKeys = T::class.hasAnnotation<IgnoreUnknownKeys>()
            encodeDefaults = T::class.hasAnnotation<EncodeDefaults>()
            explicitNulls = T::class.hasAnnotation<ExplicitNulls>()
            prettyPrint = T::class.hasAnnotation<PrettyPrint>()
        }
    }

inline fun <reified T> T.serializeToFile(file: File) {
    file.writeText(serializeToString())
}

inline fun <reified T> T.serializeToString(): String =
    jsonFor<T>().encodeToString(this)

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> File.deserializeFromFile(): T =
    jsonFor<T>().decodeFromStream(inputStream())

inline fun <reified T> String.deserializeFromString(): T =
    jsonFor<T>().decodeFromString(this)
