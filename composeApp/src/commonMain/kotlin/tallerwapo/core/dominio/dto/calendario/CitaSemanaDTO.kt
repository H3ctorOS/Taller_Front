package tallerwapo.core.dominio.dto.calendario

import kotlinx.serialization.Serializable
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.dto.CitaDTO

@Serializable
data class CitaSemanaDTO(
    val fechaInicio: Long,
    val fechaFin: Long,
    val numeroSemana: Int,

    val citasLunes: List<CitaDTO> = emptyList(),
    val citasMartes: List<CitaDTO> = emptyList(),
    val citasMiercoles: List<CitaDTO> = emptyList(),
    val citasJueves: List<CitaDTO> = emptyList(),
    val citasViernes: List<CitaDTO> = emptyList()
) {

    fun getLunesBO(): List<CitaBO> = citasLunes.map { CitaBO(it) }

    fun getMartesBO(): List<CitaBO> = citasMartes.map { CitaBO(it) }

    fun getMiercolesBO(): List<CitaBO> = citasMiercoles.map { CitaBO(it) }

    fun getJuevesBO(): List<CitaBO> = citasJueves.map { CitaBO(it) }

    fun getViernesBO(): List<CitaBO> = citasViernes.map { CitaBO(it) }
}