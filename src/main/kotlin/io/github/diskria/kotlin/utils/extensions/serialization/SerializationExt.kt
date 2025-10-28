package io.github.diskria.kotlin.utils.extensions.serialization

import io.github.diskria.kotlin.utils.Constants
import io.github.diskria.kotlin.utils.extensions.common.KotlinClass
import io.github.diskria.kotlin.utils.extensions.primitives.repeat
import io.github.diskria.kotlin.utils.serialization.annotations.EncodeDefaults
import io.github.diskria.kotlin.utils.serialization.annotations.ExplicitNulls
import io.github.diskria.kotlin.utils.serialization.annotations.IgnoreUnknownKeys
import io.github.diskria.kotlin.utils.serialization.annotations.PrettyPrint
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.newReader
import nl.adaptivity.xmlutil.serialization.DefaultXmlSerializationPolicy
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlSerializationPolicy
import nl.adaptivity.xmlutil.xmlStreaming
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.full.hasAnnotation

val jsonCache: MutableMap<KotlinClass<*>, Json> by lazy { ConcurrentHashMap() }
val xmlCache: MutableMap<KotlinClass<*>, XML> by lazy { ConcurrentHashMap() }

inline fun <reified T> jsonFor(): Json =
    jsonCache.computeIfAbsent(T::class) {
        Json {
            ignoreUnknownKeys = T::class.hasAnnotation<IgnoreUnknownKeys>()
            encodeDefaults = T::class.hasAnnotation<EncodeDefaults>()
            explicitNulls = T::class.hasAnnotation<ExplicitNulls>()
            prettyPrint = T::class.hasAnnotation<PrettyPrint>()
        }
    }

@OptIn(ExperimentalXmlUtilApi::class)
inline fun <reified T> xmlFor(): XML =
    xmlCache.computeIfAbsent(T::class) {
        XML {
            policy = DefaultXmlSerializationPolicy.Builder().apply {
                if (T::class.hasAnnotation<IgnoreUnknownKeys>()) {
                    ignoreUnknownChildren()
                }
                if (T::class.hasAnnotation<EncodeDefaults>()) {
                    encodeDefault = XmlSerializationPolicy.XmlEncodeDefault.ALWAYS
                }
            }.build()
            if (T::class.hasAnnotation<ExplicitNulls>()) {
                error("@ExplicitNulls is unsupported for XML serialization")
            }
            if (T::class.hasAnnotation<PrettyPrint>()) {
                indentString = Constants.Char.SPACE.repeat(2)
                xmlDeclMode = XmlDeclMode.Auto
            }
        }
    }

inline fun <reified T> T.serializeJsonToFile(file: File) {
    file.writeText(serializeToJson())
}

inline fun <reified T> T.serializeToJson(): String =
    jsonFor<T>().encodeToString(this)

@OptIn(ExperimentalSerializationApi::class)
inline fun <reified T> File.deserializeJsonFromFile(): T =
    jsonFor<T>().decodeFromStream(inputStream())

inline fun <reified T> String.deserializeFromJson(): T =
    jsonFor<T>().decodeFromString(this)

inline fun <reified T> T.serializeXmlToFile(file: File) {
    file.writeText(serializeToXml())
}

inline fun <reified T> T.serializeToXml(): String =
    xmlFor<T>().encodeToString(this)

inline fun <reified T> File.deserializeXmlFromFile(): T =
    inputStream().use { input ->
        xmlStreaming.newReader(input).use { reader ->
            xmlFor<T>().decodeFromReader(reader)
        }
    }

inline fun <reified T> String.deserializeFromXml(): T =
    xmlFor<T>().decodeFromString(this)
