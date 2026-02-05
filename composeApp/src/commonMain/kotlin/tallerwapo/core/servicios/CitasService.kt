package tallerwapo.core.servicios

import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.dto.CitaDTO
import kotlinx.datetime.Instant

class CitasService {
    companion object {
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

        fun toBOList(dtos: List<CitaDTO>): List<CitaBO> {
            return dtos.map { toBO(it) }
        }
    }
}