package tallerwapo.core.dominio.bo

import kotlin.time.Instant
import androidx.compose.runtime.mutableStateListOf
import tallerwapo.core.dominio.bo.interfaz.BaseBO
import tallerwapo.core.dominio.dto.CitaDTO
import tallerwapo.core.servicios.DtoService

data class CitaBO(
    override val uuid: Int = 0,
    val vehiculoUuid: Int = 0,
    val concepto: String = "",
    val fechaInicio: Instant = Instant.fromEpochMilliseconds(0),
    val fechaFinalizada: Instant = Instant.fromEpochMilliseconds(0),
    val codigoEstado: String = "",
    val observaciones: String = ""
) : BaseBO {

    // Listas reactivas de ingresos y gastos asociados a esta cita
    val ingresos = mutableStateListOf<IngresoBO>()
    val gastos = mutableStateListOf<GastoBO>()

    // Constructor secundario desde DTO
    constructor(dto: CitaDTO) : this(
        uuid = dto.uuid,
        vehiculoUuid = dto.vehiculoUuid,
        concepto = dto.concepto,
        fechaInicio = Instant.fromEpochMilliseconds(dto.fechaInicio),
        fechaFinalizada = dto.fechaFinalizada?.let { Instant.fromEpochMilliseconds(it) }
            ?: Instant.fromEpochMilliseconds(0),
        codigoEstado = dto.codigoEstado,
        observaciones = dto.observaciones ?: ""
    ) {
        // Inicializamos las listas si vienen en el DTO
        dto.ingresos?.map { DtoService.toBO(it) }?.let { ingresos.addAll(it) }
        dto.gastos?.map { DtoService.toBO(it) }?.let { gastos.addAll(it) }
    }
}
