package io.github.diskria.utils.kotlin.serialization

import io.github.diskria.utils.kotlin.extensions.common.KotlinClass
import io.github.diskria.utils.kotlin.extensions.common.failWithUnsupportedType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

open class PrimitiveValueSerializer<Raw, T : Any>(
    clazz: KotlinClass<T>,
    private val kindInfo: PrimitiveKindInfo<Raw>,
    private val fromRawValue: (Raw) -> T,
    private val toRawValue: (T) -> Raw,
) : KSerializer<T> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            clazz.simpleName ?: failWithUnsupportedType(clazz),
            kindInfo.kind
        )

    override fun serialize(encoder: Encoder, value: T) {
        kindInfo.encode(encoder, toRawValue(value))
    }

    override fun deserialize(decoder: Decoder): T =
        fromRawValue(kindInfo.decode(decoder))
}
