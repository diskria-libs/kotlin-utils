package io.github.diskria.kotlin.utils.extensions.serialization

import com.akuleshov7.ktoml.Toml
import com.akuleshov7.ktoml.TomlIndentation
import com.akuleshov7.ktoml.TomlInputConfig
import com.akuleshov7.ktoml.TomlOutputConfig
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
import kotlinx.serialization.serializer
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.serialization.DefaultXmlSerializationPolicy
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlSerializationPolicy
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.full.hasAnnotation

val jsonCache: MutableMap<KotlinClass<*>, Json> by lazy { ConcurrentHashMap() }
val xmlCache: MutableMap<KotlinClass<*>, XML> by lazy { ConcurrentHashMap() }
val tomlCache: MutableMap<KotlinClass<*>, Toml> by lazy { ConcurrentHashMap() }

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
        val ignoreUnknownKeys = T::class.hasAnnotation<IgnoreUnknownKeys>()
        val encodeDefaults = T::class.hasAnnotation<EncodeDefaults>()
        val explicitNulls = T::class.hasAnnotation<ExplicitNulls>()
        val prettyPrint = T::class.hasAnnotation<PrettyPrint>()
        if (explicitNulls) {
            error("@ExplicitNulls is unsupported for XML serialization")
        }
        XML {
            policy = DefaultXmlSerializationPolicy.Builder().apply {
                if (ignoreUnknownKeys) {
                    ignoreUnknownChildren()
                }
                if (encodeDefaults) {
                    encodeDefault = XmlSerializationPolicy.XmlEncodeDefault.ALWAYS
                }
            }.build()
            if (prettyPrint) {
                indentString = Constants.Char.SPACE.repeat(4)
                xmlDeclMode = XmlDeclMode.Auto
            }
        }
    }

inline fun <reified T> tomlFor(): Toml =
    tomlCache.computeIfAbsent(T::class) {
        val ignoreUnknownKeys = T::class.hasAnnotation<IgnoreUnknownKeys>()
        val encodeDefaults = T::class.hasAnnotation<EncodeDefaults>()
        val explicitNulls = T::class.hasAnnotation<ExplicitNulls>()
        val prettyPrint = T::class.hasAnnotation<PrettyPrint>()
        if (explicitNulls) {
            error("@ExplicitNulls is unsupported for TOML serialization")
        }
        Toml(
            inputConfig = TomlInputConfig(
                ignoreUnknownNames = ignoreUnknownKeys,
//                ignoreDefaultValues = !encodeDefaults,
            ),
            outputConfig = TomlOutputConfig(
                indentation = if (prettyPrint) TomlIndentation.FOUR_SPACES else TomlIndentation.NONE,
                ignoreDefaultValues = !encodeDefaults,
                explicitTables = true,
            )
        )
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

//inline fun <reified T> File.deserializeXmlFromFile(): T =
//    inputStream().use { input ->
//        xmlStreaming.newReader(input).use { reader ->
//            xmlFor<T>().decodeFromReader(reader)
//        }
//    }

inline fun <reified T> String.deserializeFromXml(): T =
    xmlFor<T>().decodeFromString(this)

inline fun <reified T> T.serializeTomlToFile(file: File) {
    file.writeText(serializeToToml())
}

inline fun <reified T> T.serializeToToml(): String =
    tomlFor<T>().encodeToString(serializer(), this)

inline fun <reified T> File.deserializeTomlFromFile(): T =
    tomlFor<T>().decodeFromString(serializer(), readText())

inline fun <reified T> String.deserializeFromToml(): T =
    tomlFor<T>().decodeFromString(serializer(), this)
