package io.github.diskria.kotlin.utils

import io.github.diskria.kotlin.utils.extensions.primitives.isNonNegative
import io.github.diskria.kotlin.utils.extensions.primitives.orZero
import io.github.diskria.kotlin.utils.extensions.primitives.positiveOrNull
import io.github.diskria.kotlin.utils.extensions.splitToTripleOrNull
import io.github.diskria.kotlin.utils.extensions.wrapWithSingleQuote
import kotlinx.serialization.Serializable

@Serializable
data class Semver(val major: Int, val minor: Int, val patch: Int) : Comparable<Semver> {

    fun toVersion(dropZeroPatch: Boolean = false): String =
        listOfNotNull(
            major,
            minor,
            if (dropZeroPatch) patch.takeUnless { it == 0 } else patch
        ).joinToString(Constants.Char.DOT.toString())

    override fun compareTo(other: Semver): Int =
        compareValuesBy(this, other, Semver::major, Semver::minor, Semver::patch)

    companion object {
        fun parseOrNull(version: String): Semver? {
            val (major, minor, patch) = version.splitToTripleOrNull(Constants.Char.DOT) ?: return null
            return from(
                major.toSegmentOrNull() ?: return null,
                minor.toSegmentOrNull() ?: return null,
                patch.toSegmentOrNull() ?: return null,
            )
        }

        fun parse(version: String): Semver =
            parseOrNull(version) ?: error("Version ${version.wrapWithSingleQuote()} is not a valid SemVer")

        fun from(major: Int, minor: Int? = null, patch: Int? = null): Semver {
            val stableMinor = minor.orZero()
            val stablePatch = patch.orZero()
            require(major.isNonNegative() && stableMinor.isNonNegative() && stablePatch.isNonNegative()) {
                "SemVer segments must be non-negative"
            }
            return Semver(major, stableMinor, stablePatch)
        }

        private fun String.toSegmentOrNull(): Int? =
            toIntOrNull()?.positiveOrNull()
    }
}
