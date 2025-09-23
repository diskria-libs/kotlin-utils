package io.github.diskria.kotlin.utils.serialization

import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

interface PrimitiveKindInfo<T> {
    val kind: PrimitiveKind

    fun encode(encoder: Encoder, value: T)
    fun decode(decoder: Decoder): T
}
