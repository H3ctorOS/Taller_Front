package tallerwapo.core.apirest.implementaciones

import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.apirest.ApiConfig.BASE_URL
import tallerwapo.core.apirest.ApiRest
import tallerwapo.core.apirest.interfaces.CitasApi
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.dto.CitaDTO
import tallerwapo.core.servicios.CitasService


class CitasApiImpl(): CitasApi {

    override suspend fun crearCita(cita: CitaBO): RespuestaDTO<CitaBO> {
        return ApiRest.post(
            url = BASE_URL + CREAR_NUEVA,
            body = cita
        )
    }

    override suspend fun buscarTodas(): RespuestaDTO<List<CitaBO>> {
        // 1️⃣ Llamar al backend y obtener la lista de DTOs
        val response: RespuestaDTO<List<CitaDTO>> =
            ApiRest.get(url = BASE_URL + BUSCAR_TODAS)

        // 2️⃣ Convertir la lista de DTOs a BOs directamente
        val boList = response.BoRespuesta?.let { CitasService.toBOList(it) } ?: emptyList()

        // 3️⃣ Retornar un RespuestaDTO con la lista de BOs
        return RespuestaDTO(
            status = response.status,
            mensaje = response.mensaje,
            BoRespuesta = boList,
            isOk = response.isOk
        )
    }

    override suspend fun buscarPorVehiculo(vehiculoBO: VehiculoBO): RespuestaDTO<List<CitaBO>> {
        // 1️⃣ Llamar al backend pasando el parámetro del vehículo
        val response: RespuestaDTO<List<CitaDTO>> =
            ApiRest.get(
                url = BASE_URL + BUSCAR_POR_VEHICULO,
                params = mapOf("vehiculoUuid" to vehiculoBO.uuid)
            )

        // 2️⃣ Convertir la lista de DTOs a BOs directamente
        val boList = response.BoRespuesta?.let { CitasService.toBOList(it) } ?: emptyList()

        // 3️⃣ Retornar un RespuestaDTO con la lista de BOs
        return RespuestaDTO(
            status = response.status,
            mensaje = response.mensaje,
            BoRespuesta = boList,
            isOk = response.isOk
        )
    }

}