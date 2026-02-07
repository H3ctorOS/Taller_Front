package tallerwapo.core.dominio.dto

import kotlinx.serialization.Serializable
import tallerwapo.core.dominio.bo.CitaBO


@Serializable
data class CitaDTO(
    val uuid: Int,
    val vehiculoUuid: Int,
    val concepto: String,
    val fechaInicio: Long,
    val fechaFinalizada: Long?,
    val codigoEstado: String,
    val observaciones: String?
) {

    // Constructor secundario desde BO
    constructor(bo: CitaBO) : this(
        uuid = bo.uuid,
        vehiculoUuid = bo.vehiculoUuid,
        concepto = bo.concepto,
        fechaInicio = bo.fechaInicio.toEpochMilliseconds(),
        fechaFinalizada = bo.fechaFinalizada?.toEpochMilliseconds(),
        codigoEstado = bo.codigoEstado,
        observaciones = bo.observaciones
    )
}