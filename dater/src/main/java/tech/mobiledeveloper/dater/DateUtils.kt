package tech.mobiledeveloper.dater

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    fun formatDate(date: Date, pattern: String = "yyyy-MM-dd"): String {
        val simpleDateFormatter = SimpleDateFormat(pattern, Locale.getDefault())
        return simpleDateFormatter.format(date)
    }
}