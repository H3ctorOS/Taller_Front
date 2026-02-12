package tallerwapo.core.servicios

import tallerwapo.core.contexto.AppContexto
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


    fun getAnioActual(): Int {
        return java.time.ZonedDateTime.now(zone).year
    }


     suspend fun getSemanasAnioActual(): SemanasDelAnioDTO? {
        val api = AppContexto.gestionServidorApi

        return api.getSemanasAnio(getAnioActual())
    }



}
