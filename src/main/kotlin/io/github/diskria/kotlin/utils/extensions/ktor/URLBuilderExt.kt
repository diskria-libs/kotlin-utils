package io.github.diskria.kotlin.utils.extensions.ktor

import io.ktor.http.*

fun URLBuilder.parameters(vararg pairs: Pair<String, String>) {
    pairs.forEach { (key, value) ->
        parameters.append(key, value)
    }
}

fun URLBuilder.parameters(map: Map<String, String>) =
    parameters(*map.toList().toTypedArray())
