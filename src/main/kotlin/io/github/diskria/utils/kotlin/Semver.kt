package io.github.diskria.utils.kotlin

import io.github.diskria.utils.kotlin.extensions.generics.joinToString
import io.github.diskria.utils.kotlin.extensions.primitives.orZero
import io.github.diskria.utils.kotlin.extensions.splitToTriple

data class Semver(val major: Int, val minor: Int, val patch: Int): Comparable<Semver> {

    override fun compareTo(other: Semver): Int =
        compareValuesBy(this, other, Semver::major, Semver::minor, Semver::patch)

    override fun toString(): String =
        listOf(major, minor, patch).joinToString(Constants.Char.DOT)

    companion object {
        fun of(versionName: String): Semver {
            val (major, minor, patch) = versionName
                .splitToTriple(Constants.Char.DOT)
                .toList()
                .map { it?.toIntOrNull().orZero() }
            return Semver(major, minor, patch)
        }
    }
}
