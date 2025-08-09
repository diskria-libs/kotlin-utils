package io.github.diskria.utils.kotlin.extensions

import io.github.diskria.utils.kotlin.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(format: DateFormat, locale: Locale = Locale.US): String =
    SimpleDateFormat(format.pattern, locale).format(this)
