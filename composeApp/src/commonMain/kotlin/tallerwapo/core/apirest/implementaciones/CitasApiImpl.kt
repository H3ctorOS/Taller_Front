package tallerwapo.core.apirest.implementaciones

import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.dominio.dto.CitaDTO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.apirest.ApiConfig.BASE_URL
import tallerwapo.core.apirest.ApiRest
import tallerwapo.core.apirest.interfaces.CitasApi
import tallerwapo.core.servicios.DtoService
import tallerwapo.core.servicios.DtoService.Companion.toBOList

class CitasApiImpl : CitasApi {

    override suspend fun crearCita(cita: CitaBO): RespuestaDTO<CitaBO> {
        return ApiRest.post(
            url = BASE_URL + CREAR_NUEVA,
            body = cita
        )
    }

    override suspend fun buscarTodas(): RespuestaDTO<List<CitaBO>> {
        val response: RespuestaDTO<List<CitaDTO>> =
            ApiRest.get(url = BASE_URL + BUSCAR_TODAS)

        val boList = response.BoRespuesta?.toBOList { dto -> DtoService.toBO(dto) } ?: emptyList()

        return RespuestaDTO(
            status = response.status,
            mensaje = response.mensaje,
            BoRespuesta = boList,
            isOk = response.isOk
        )
    }

    override suspend fun buscarPorVehiculo(vehiculoBO: VehiculoBO): RespuestaDTO<List<CitaBO>> {
        val response: RespuestaDTO<List<CitaDTO>> =
            ApiRest.get(
                url = BASE_URL + BUSCAR_POR_VEHICULO,
                params = mapOf("vehiculoUuid" to vehiculoBO.uuid)
            )

        val boList = response.BoRespuesta?.toBOList { dto -> DtoService.toBO(dto) } ?: emptyList()

        return RespuestaDTO(
            status = response.status,
            mensaje = response.mensaje,
            BoRespuesta = boList,
            isOk = response.isOk
        )
    }
}
