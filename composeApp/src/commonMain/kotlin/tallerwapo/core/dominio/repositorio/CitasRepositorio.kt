package tallerwapo.core.dominio.repositorio

import tallerwapo.core.apirest.interfaces.CitasApi
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.dominio.dto.CitaDTO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.dto.VehiculoDTO
import tallerwapo.core.utils.Logs

class CitasRepositorio(
    private val apiRest: CitasApi
) {

    // Crear cita: recibe BO, convierte a DTO para la API, devuelve BO
    suspend fun crearCita(cita: CitaBO): RespuestaDTO<CitaBO> {
        Logs.info(this, "Creando nueva cita: $cita")

        // BO -> DTO usando constructor del DTO
        val dto = CitaDTO(cita)

        val respuestaDTO: RespuestaDTO<CitaDTO> = apiRest.crearCita(dto)

        // DTO -> BO usando constructor de CitaBO
        val boRespuesta = respuestaDTO.BoRespuesta?.let { CitaBO(it) }

        val respuestaBO = RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )

        Logs.info(this, "Respuesta al crear la cita: ${respuestaBO.mensaje}")
        return respuestaBO
    }

    // Buscar todas las citas
    suspend fun buscarTodas(): RespuestaDTO<List<CitaBO>> {
        Logs.info(this, "Buscando todas las citas")
        val respuestaDTO: RespuestaDTO<List<CitaDTO>> = apiRest.buscarTodas()

        // Convertir lista de DTOs -> lista de BOs
        val boList = respuestaDTO.BoRespuesta?.map { CitaBO(it) } ?: emptyList()

        val respuesta = RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boList,
            isOk = respuestaDTO.isOk
        )

        Logs.info(this, "Cantidad de citas recibidas: ${boList.size}")
        return respuesta
    }

    // Buscar citas por vehículo
    suspend fun buscarPorVehiculo(vehiculoBO: VehiculoBO): RespuestaDTO<List<CitaBO>> {
        Logs.info(this, "Buscando citas del vehiculo: $vehiculoBO")

        // BO -> DTO usando constructor del DTO
        val vehiculoDTO = VehiculoDTO(vehiculoBO)

        val respuestaDTO: RespuestaDTO<List<CitaDTO>> = apiRest.buscarPorVehiculo(vehiculoDTO)

        // Convertir lista de DTOs -> lista de BOs
        val boList = respuestaDTO.BoRespuesta?.map { CitaBO(it) } ?: emptyList()

        val respuestaBO = RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boList,
            isOk = respuestaDTO.isOk
        )

        Logs.info(this, "Cantidad de citas recibidas: ${boList.size}")
        return respuestaBO
    }
}
