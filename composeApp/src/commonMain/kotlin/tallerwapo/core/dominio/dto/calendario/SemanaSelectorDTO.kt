package tallerwapo.core.dominio.dto.calendario

import kotlinx.serialization.Serializable

@Serializable
data class SemanaSelectorDTO(
    val numeroSemana: Int,
    val anio: Int,
    val esActual: Boolean // True si es la semana actual
)