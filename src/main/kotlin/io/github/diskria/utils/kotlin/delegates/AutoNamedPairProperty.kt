package io.github.diskria.utils.kotlin.delegates

class AutoNamedPairProperty<T>(second: T) : AutoNamedProperty<Pair<String, T>>(
    { propertyName -> propertyName to second }
)

inline fun <reified T> T.toAutoNamedPair(): AutoNamedPairProperty<T> = AutoNamedPairProperty(this)
