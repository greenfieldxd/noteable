package greenfieldxd.noteable.presentation.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toDateTime(): String {
    val dateTime: LocalDateTime = Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
   return dateTime.format(formatter)
}