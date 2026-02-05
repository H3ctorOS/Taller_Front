package tallerwapo.core.servicios

import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.Instant
import kotlin.time.toJavaInstant


@Suppress("NewApi")
fun Instant.formatoDiaMesAnio(): String {
    val zdt = this.toJavaInstant().atZone(ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return zdt.format(formatter)
}
