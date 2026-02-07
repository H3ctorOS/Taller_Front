package tallerwapo.core.dominio.dto.contabilidad

import kotlinx.serialization.Serializable

@Serializable
data class GastoDTO(
    val uuid: Int,
    val descripcion: String,
    val importe: Double,
    val fecha: Long,
    val observaciones: String?
)