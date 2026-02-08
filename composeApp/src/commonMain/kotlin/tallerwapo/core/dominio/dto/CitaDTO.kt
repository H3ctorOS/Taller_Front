package tallerwapo.core.dominio.dto

import kotlinx.serialization.Serializable
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.GastoBO
import tallerwapo.core.dominio.bo.IngresoBO
import tallerwapo.core.dominio.dto.contabilidad.GastoDTO
import tallerwapo.core.dominio.dto.contabilidad.IngresoDTO

@Serializable
data class CitaDTO(
    val uuid: Int,
    val vehiculoUuid: Int,
    val concepto: String,
    val fechaInicio: Long,
    val fechaFinalizada: Long?,
    val codigoEstado: String,
    val observaciones: String?,
    val ingresos: List<IngresoDTO>? = null,
    val gastos: List<GastoDTO>? = null
) {
    // Constructor secundario desde BO
    constructor(bo: CitaBO) : this(
        uuid = bo.uuid,
        vehiculoUuid = bo.vehiculoUuid,
        concepto = bo.concepto,
        fechaInicio = bo.fechaInicio.toEpochMilliseconds(),
        fechaFinalizada = bo.fechaFinalizada.toEpochMilliseconds(),
        codigoEstado = bo.codigoEstado,
        observaciones = bo.observaciones,
        ingresos = bo.ingresos.map { ingreso ->
            IngresoDTO(
                uuid = ingreso.uuid,
                concepto = ingreso.concepto,
                importe = ingreso.importe,
                fecha = ingreso.fecha.toEpochMilliseconds(),
                codEstado = ingreso.codEstado,
                observaciones = ingreso.observaciones
            )
        },
        gastos = bo.gastos.map { gasto ->
            GastoDTO(
                uuid = gasto.uuid,
                descripcion = gasto.descripcion,
                importe = gasto.importe,
                fecha = gasto.fecha.toEpochMilliseconds(),
                observaciones = gasto.observaciones
            )
        }
    )
}
