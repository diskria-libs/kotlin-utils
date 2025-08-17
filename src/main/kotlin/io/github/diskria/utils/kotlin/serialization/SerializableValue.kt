package io.github.diskria.utils.kotlin.serialization

import io.github.diskria.utils.kotlin.extensions.common.KotlinSerializer
import io.github.diskria.utils.kotlin.extensions.common.failWithUnsupportedType
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

interface SerializableValue<Raw> {
    fun getRawValue(): Raw
}

inline fun <reified Raw, reified T : SerializableValue<Raw>> valueSerializer(
    noinline rawMapper: (Raw) -> T,
): KotlinSerializer<T> {

    val kindInfo = object : PrimitiveKindInfo<Raw> {

        override val kind: PrimitiveKind
            get() = when (Raw::class) {
                String::class -> PrimitiveKind.STRING
                Int::class -> PrimitiveKind.INT
                Boolean::class -> PrimitiveKind.BOOLEAN
                else -> failWithUnsupportedType(Raw::class)
            }

        override fun encode(encoder: Encoder, value: Raw) {
            when (value) {
                is String -> encoder.encodeString(value)
                is Int -> encoder.encodeInt(value)
                is Boolean -> encoder.encodeBoolean(value)
                else -> failWithUnsupportedType(value::class)
            }
        }

        override fun decode(decoder: Decoder): Raw =
            when (Raw::class) {
                String::class -> decoder.decodeString()
                Int::class -> decoder.decodeInt()
                Boolean::class -> decoder.decodeBoolean()
                else -> failWithUnsupportedType(Raw::class)
            } as Raw
    }

    return PrimitiveValueSerializer(
        clazz = T::class,
        kindInfo = kindInfo,
        fromRawValue = rawMapper,
        toRawValue = { serializableValue -> serializableValue.getRawValue() }
    )
}
