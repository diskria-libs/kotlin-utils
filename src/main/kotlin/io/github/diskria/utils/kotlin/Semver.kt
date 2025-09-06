package io.github.diskria.utils.kotlin

import io.github.diskria.utils.kotlin.extensions.generics.joinToString
import io.github.diskria.utils.kotlin.extensions.primitives.orZero
import io.github.diskria.utils.kotlin.extensions.primitives.positiveOrNull
import io.github.diskria.utils.kotlin.extensions.splitToTriple

data class Semver(val major: Int, val minor: Int, val patch: Int) : Comparable<Semver> {

    override fun compareTo(other: Semver): Int =
        compareValuesBy(this, other, Semver::major, Semver::minor, Semver::patch)

    override fun toString(): String =
        listOf(major, minor, patch).joinToString(Constants.Char.DOT)

    companion object {
        val MIN_VALUE: Semver = Semver(0, 0, 0)
        val MAX_VALUE: Semver = Semver(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)

        fun of(version: String): Semver {
            val (major, minor, patch) = version
                .splitToTriple(Constants.Char.DOT)
                .toList()
                .map { it?.toIntOrNull()?.positiveOrNull().orZero() }
            return Semver(major, minor, patch)
        }
    }
}
