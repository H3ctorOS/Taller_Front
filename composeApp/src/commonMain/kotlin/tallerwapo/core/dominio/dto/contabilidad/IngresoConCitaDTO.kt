package tallerwapo.core.dominio.dto.contabilidad

import tallerwapo.core.dominio.dto.CitaDTO

@kotlinx.serialization.Serializable
data class IngresoConCitaDTO(
    val ingreso: IngresoDTO,
    val cita: CitaDTO
)