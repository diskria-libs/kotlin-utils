package io.github.diskria.utils.kotlin.extensions.generics

fun <E> MutableCollection<E>.addAll(vararg elements: E) {
    addAll(elements)
}
