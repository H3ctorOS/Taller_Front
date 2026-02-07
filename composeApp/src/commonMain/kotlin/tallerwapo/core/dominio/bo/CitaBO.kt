package tallerwapo.core.dominio.bo

import kotlin.time.Instant
import tallerwapo.core.dominio.bo.interfaz.BaseBO
import tallerwapo.core.dominio.dto.CitaDTO

data class CitaBO(
    override val uuid: Int = 0,
    val vehiculoUuid: Int = 0,
    val concepto: String = "",
    val fechaInicio: Instant = Instant.fromEpochMilliseconds(0),
    val fechaFinalizada: Instant = Instant.fromEpochMilliseconds(0),
    val codigoEstado: String = "",
    val observaciones: String = ""
) : BaseBO {

    // Constructor secundario desde DTO
    constructor(dto: CitaDTO) : this(
        uuid = dto.uuid,
        vehiculoUuid = dto.vehiculoUuid,
        concepto = dto.concepto,
        fechaInicio = Instant.fromEpochMilliseconds(dto.fechaInicio),
        fechaFinalizada = dto.fechaFinalizada?.let { Instant.fromEpochMilliseconds(it) } ?: Instant.fromEpochMilliseconds(0),
        codigoEstado = dto.codigoEstado,
        observaciones = dto.observaciones ?: ""
    )
}
