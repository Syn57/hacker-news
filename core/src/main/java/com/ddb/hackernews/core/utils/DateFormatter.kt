package com.ddb.hackernews.core.utils

import java.text.SimpleDateFormat
import java.util.Locale
import com.ddb.hackernews.core.BuildConfig

object DateFormatter {
    fun format(input: Long): String {
        val sdf = SimpleDateFormat(BuildConfig.DATE_FORMAT, Locale.TAIWAN)
        return sdf.format(input)
    }
}