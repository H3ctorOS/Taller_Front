package tallerwapo.core.dominio.dto.calendario

import kotlinx.serialization.Serializable
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.dto.CitaDTO

// Enum propio para los días de la semana (compatible con minSdk 24)
enum class DiaSemana(val valor: Int) {
    lunes(1),
    martes(2),
    miércoles(3),
    jueves(4),
    viernes(5),
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
    fun getLunesBO() = getCitasBO(DiaSemana.lunes)
    fun getMartesBO() = getCitasBO(DiaSemana.martes)
    fun getMiercolesBO() = getCitasBO(DiaSemana.miércoles)
    fun getJuevesBO() = getCitasBO(DiaSemana.jueves)
    fun getViernesBO() = getCitasBO(DiaSemana.viernes)

    // ───────── Métodos para obtener pares fecha + citas ─────────
    fun getFechaConCitas(dia: DiaSemana): Pair<Long, List<CitaBO>> =
        (fechas[dia] ?: -1L) to getCitasBO(dia)

    fun getLunesConFecha() = getFechaConCitas(DiaSemana.lunes)
    fun getMartesConFecha() = getFechaConCitas(DiaSemana.martes)
    fun getMiercolesConFecha() = getFechaConCitas(DiaSemana.miércoles)
    fun getJuevesConFecha() = getFechaConCitas(DiaSemana.jueves)
    fun getViernesConFecha() = getFechaConCitas(DiaSemana.viernes)
}
