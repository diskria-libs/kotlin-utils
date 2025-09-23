package io.github.diskria.kotlin.utils.poet

open class Property<out T>(val name: String, val value: T) {

    fun toPair(): Pair<String, T> =
        name to value
}
