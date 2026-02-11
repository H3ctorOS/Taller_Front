package tallerwapo.core.servicios

import tallerwapo.core.dominio.dto.calendario.SemanaSelectorDTO
import tallerwapo.core.dominio.dto.calendario.SemanasDelAnioDTO
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



    fun getSemanasDelActual(): SemanasDelAnioDTO {
        val anio = 2026
        val semanasPorMes = mutableMapOf<Int, List<SemanaSelectorDTO>>()

        // Simulación: 4 semanas por mes
        for (mes in 1..12) {
            val semanas = mutableListOf<SemanaSelectorDTO>()
            for (semanaNum in 1..4) {
                // Creamos la semana simulada, combinando número de semana y año
                semanas.add(SemanaSelectorDTO(numeroSemana = semanaNum + (mes - 1) * 4, anio = anio))
            }
            semanasPorMes[mes] = semanas
        }

        return SemanasDelAnioDTO(semanasPorMes)
    }



}
