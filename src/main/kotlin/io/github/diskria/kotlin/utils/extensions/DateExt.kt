package io.github.diskria.kotlin.utils.extensions

import io.github.diskria.kotlin.utils.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(format: DateFormat, locale: Locale = Locale.US): String =
    SimpleDateFormat(format.pattern, locale).format(this)
