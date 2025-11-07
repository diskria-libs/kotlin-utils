package io.github.diskria.kotlin.utils.helpers

import io.github.diskria.kotlin.utils.extensions.common.buildEmail

enum class EmailType(val domain: String) {

    GMAIL("gmail.com"),
    PROTON("proton.me");

    fun buildAddress(localPart: String): String =
        buildEmail(localPart, this)
}
