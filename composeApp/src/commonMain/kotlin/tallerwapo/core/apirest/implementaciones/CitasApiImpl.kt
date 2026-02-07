package tallerwapo.core.apirest.implementaciones

import tallerwapo.core.dominio.dto.CitaDTO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.dto.VehiculoDTO
import tallerwapo.core.apirest.ApiConfig.BASE_URL
import tallerwapo.core.apirest.ApiRest
import tallerwapo.core.apirest.interfaces.CitasApi

class CitasApiImpl : CitasApi {

    override suspend fun crearCita(cita: CitaDTO): RespuestaDTO<CitaDTO> {
        return ApiRest.post(
            url = BASE_URL + CREAR_NUEVA,
            body = cita
        )
    }

    override suspend fun buscarTodas(): RespuestaDTO<List<CitaDTO>> {
        return ApiRest.get(
            url = BASE_URL + BUSCAR_TODAS
        )
    }

    override suspend fun buscarPorVehiculo(vehiculo: VehiculoDTO): RespuestaDTO<List<CitaDTO>> {
        return ApiRest.get(
            url = BASE_URL + BUSCAR_POR_VEHICULO,
            params = mapOf("vehiculoUuid" to vehiculo.uuid)
        )
    }
}
