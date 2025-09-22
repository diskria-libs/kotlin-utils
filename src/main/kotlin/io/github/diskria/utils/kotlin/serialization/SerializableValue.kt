package io.github.diskria.utils.kotlin.serialization

import io.github.diskria.utils.kotlin.extensions.common.KotlinSerializer
import io.github.diskria.utils.kotlin.extensions.common.failWithUnsupportedType
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

interface SerializableValue<R> {
    fun getRawValue(): R
}

inline fun <reified R, reified T : SerializableValue<R>> valueSerializer(
    noinline rawMapper: (R) -> T,
): KotlinSerializer<T> {

    val kindInfo = object : PrimitiveKindInfo<R> {

        override val kind: PrimitiveKind
            get() = when (R::class) {
                String::class -> PrimitiveKind.STRING
                Int::class -> PrimitiveKind.INT
                Boolean::class -> PrimitiveKind.BOOLEAN
                else -> failWithUnsupportedType(R::class)
            }

        override fun encode(encoder: Encoder, value: R) {
            when (value) {
                is String -> encoder.encodeString(value)
                is Int -> encoder.encodeInt(value)
                is Boolean -> encoder.encodeBoolean(value)
                else -> failWithUnsupportedType(value::class)
            }
        }

        override fun decode(decoder: Decoder): R =
            when (R::class) {
                String::class -> decoder.decodeString()
                Int::class -> decoder.decodeInt()
                Boolean::class -> decoder.decodeBoolean()
                else -> failWithUnsupportedType(R::class)
            } as R
    }

    return PrimitiveValueSerializer(
        clazz = T::class,
        kindInfo = kindInfo,
        fromRawValue = rawMapper,
        toRawValue = { serializableValue -> serializableValue.getRawValue() }
    )
}
