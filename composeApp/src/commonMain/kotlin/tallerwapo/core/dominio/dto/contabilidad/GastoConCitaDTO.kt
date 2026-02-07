package tallerwapo.core.dominio.dto.contabilidad

import kotlinx.serialization.Serializable
import tallerwapo.core.dominio.dto.CitaDTO


@Serializable
data class GastoConCitaDTO(
    val gasto: GastoDTO,
    val cita: CitaDTO
)