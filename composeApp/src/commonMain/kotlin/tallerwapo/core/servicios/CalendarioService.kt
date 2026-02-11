package tallerwapo.core.servicios

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.Instant
import kotlin.time.toJavaInstant

@Suppress("NewApi")
object CalendarioService {

    private val zone: ZoneId = ZoneId.systemDefault()
    private val formatterDiaMesAnio = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    fun formatoDiaMesAnio(instant: Instant): String {
        val zdt = instant.toJavaInstant().atZone(zone)
        return zdt.format(formatterDiaMesAnio)
    }

    /* -------------------------------------------------------
       ENUM DIAS LABORALES
    ------------------------------------------------------- */

    enum class DiaSemana {
        LUNES,
        MARTES,
        MIERCOLES,
        JUEVES,
        VIERNES
    }
}
