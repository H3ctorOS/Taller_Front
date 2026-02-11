package tallerwapo.core.dominio.dto.calendario

import kotlinx.serialization.Serializable
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.dto.CitaDTO

// Enum propio para los días de la semana (compatible con minSdk 24)
enum class DiaSemana(val valor: Int) {
    LUNES(1),
    MARTES(2),
    MIERCOLES(3),
    JUEVES(4),
    VIERNES(5),
    SABADO(6),
    DOMINGO(7)
}

@Serializable
data class CitaSemanaDTO(
    val numeroSemana: Int,
    val fechas: Map<DiaSemana, Long> = emptyMap(),
    val citas: Map<DiaSemana, List<CitaDTO>> = emptyMap()
) {

    // ───────── Métodos para obtener BO ─────────
    fun getCitasBO(dia: DiaSemana): List<CitaBO> =
        citas[dia]?.map { CitaBO(it) } ?: emptyList()

    // Métodos específicos por día (compatibilidad con código antiguo)
    fun getLunesBO() = getCitasBO(DiaSemana.LUNES)
    fun getMartesBO() = getCitasBO(DiaSemana.MARTES)
    fun getMiercolesBO() = getCitasBO(DiaSemana.MIERCOLES)
    fun getJuevesBO() = getCitasBO(DiaSemana.JUEVES)
    fun getViernesBO() = getCitasBO(DiaSemana.VIERNES)

    // ───────── Métodos para obtener pares fecha + citas ─────────
    fun getFechaConCitas(dia: DiaSemana): Pair<Long, List<CitaBO>> =
        (fechas[dia] ?: -1L) to getCitasBO(dia)

    fun getLunesConFecha() = getFechaConCitas(DiaSemana.LUNES)
    fun getMartesConFecha() = getFechaConCitas(DiaSemana.MARTES)
    fun getMiercolesConFecha() = getFechaConCitas(DiaSemana.MIERCOLES)
    fun getJuevesConFecha() = getFechaConCitas(DiaSemana.JUEVES)
    fun getViernesConFecha() = getFechaConCitas(DiaSemana.VIERNES)
}
