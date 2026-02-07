package tallerwapo.core.dominio.dto.contabilidad

import kotlinx.serialization.Serializable

@Serializable
data class IngresoDTO(
    val uuid: Int,
    val concepto: String,
    val importe: Double,
    val fecha: Long,
    val codEstado: String,
    val observaciones: String?
)