package io.github.diskria.utils.kotlin.extensions.generics

fun <E> MutableCollection<E>.addElements(vararg elements: E) {
    addAll(elements)
}

fun <T> Collection<T>?.toNullIfEmpty(): Collection<T>? =
    this?.ifEmpty { null }
