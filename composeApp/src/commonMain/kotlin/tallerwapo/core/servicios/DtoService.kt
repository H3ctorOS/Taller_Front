package tallerwapo.core.servicios

import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.dto.CitaDTO
import kotlin.time.Instant
import tallerwapo.core.dominio.bo.GastoBO
import tallerwapo.core.dominio.bo.IngresoBO
import tallerwapo.core.dominio.dto.contabilidad.GastoDTO
import tallerwapo.core.dominio.dto.contabilidad.IngresoDTO

class DtoService {

    companion object {

        // =========================
        // CitaDTO -> CitaBO
        // =========================
        fun toBO(dto: CitaDTO): CitaBO {
            return CitaBO(
                uuid = dto.uuid,
                vehiculoUuid = dto.vehiculoUuid,
                concepto = dto.concepto,
                fechaInicio = Instant.fromEpochMilliseconds(dto.fechaInicio),
                fechaFinalizada = dto.fechaFinalizada?.let { Instant.fromEpochMilliseconds(it) }
                    ?: Instant.fromEpochMilliseconds(0),
                codigoEstado = dto.codigoEstado,
                observaciones = dto.observaciones ?: ""
            )
        }

        // =========================
        // IngresoDTO -> IngresoBO
        // =========================
        fun toBO(dto: IngresoDTO): IngresoBO {
            return IngresoBO(
                uuid = dto.uuid,
                concepto = dto.concepto,
                importe = dto.importe,
                fecha = Instant.fromEpochMilliseconds(dto.fecha),
                codEstado = dto.codEstado,
                observaciones = dto.observaciones ?: ""
            )
        }

        // =========================
        // GastoDTO -> GastoBO
        // =========================
        fun toBO(dto: GastoDTO): GastoBO {
            return GastoBO(
                uuid = dto.uuid,
                descripcion = dto.descripcion,
                importe = dto.importe,
                fecha = Instant.fromEpochMilliseconds(dto.fecha),
                observaciones = dto.observaciones ?: ""
            )
        }

        // =========================
        // CitaBO -> CitaDTO
        // =========================
        fun toDTO(bo: CitaBO): CitaDTO {
            return CitaDTO(
                uuid = bo.uuid,
                vehiculoUuid = bo.vehiculoUuid,
                concepto = bo.concepto,
                fechaInicio = bo.fechaInicio.toEpochMilliseconds(),
                fechaFinalizada = bo.fechaFinalizada.toEpochMilliseconds(),
                codigoEstado = bo.codigoEstado,
                observaciones = bo.observaciones
            )
        }

        // =========================
        // IngresoBO -> IngresoDTO
        // =========================
        fun toDTO(bo: IngresoBO): IngresoDTO {
            return IngresoDTO(
                uuid = bo.uuid,
                concepto = bo.concepto,
                importe = bo.importe,
                fecha = bo.fecha.toEpochMilliseconds(),
                codEstado = bo.codEstado,
                observaciones = bo.observaciones
            )
        }

        // =========================
        // GastoBO -> GastoDTO
        // =========================
        fun toDTO(bo: GastoBO): GastoDTO {
            return GastoDTO(
                uuid = bo.uuid,
                descripcion = bo.descripcion,
                importe = bo.importe,
                fecha = bo.fecha.toEpochMilliseconds(),
                observaciones = bo.observaciones
            )
        }

        // =========================
        // Función genérica para cualquier DTO -> BO
        // =========================
        fun <DTO, BO> List<DTO>.toBOList(mapper: (DTO) -> BO): List<BO> {
            return this.map { mapper(it) }
        }
    }
}
