package tallerwapo.core.dominio.dto.calendario

import kotlinx.serialization.Serializable
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.dto.CitaDTO

@Serializable
data class CitaSemanaDTO(
    val numeroSemana: Int,

    val fechaLunes: Long,
    val fechaMartes: Long,
    val fechaMiercoles: Long,
    val fechaJueves: Long,
    val fechaViernes: Long,

    val citasLunes: List<CitaDTO> = emptyList(),
    val citasMartes: List<CitaDTO> = emptyList(),
    val citasMiercoles: List<CitaDTO> = emptyList(),
    val citasJueves: List<CitaDTO> = emptyList(),
    val citasViernes: List<CitaDTO> = emptyList()
) {

    // ───────── Métodos para obtener BO ─────────
    fun getLunesBO(): List<CitaBO> = citasLunes.map { CitaBO(it) }
    fun getMartesBO(): List<CitaBO> = citasMartes.map { CitaBO(it) }
    fun getMiercolesBO(): List<CitaBO> = citasMiercoles.map { CitaBO(it) }
    fun getJuevesBO(): List<CitaBO> = citasJueves.map { CitaBO(it) }
    fun getViernesBO(): List<CitaBO> = citasViernes.map { CitaBO(it) }

    // ───────── Métodos para obtener pares fecha + citas ─────────
    fun getLunesConFecha() = fechaLunes to getLunesBO()
    fun getMartesConFecha() = fechaMartes to getMartesBO()
    fun getMiercolesConFecha() = fechaMiercoles to getMiercolesBO()
    fun getJuevesConFecha() = fechaJueves to getJuevesBO()
    fun getViernesConFecha() = fechaViernes to getViernesBO()
}
