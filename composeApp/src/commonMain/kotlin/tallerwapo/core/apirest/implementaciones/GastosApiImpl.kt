package tallerwapo.core.apirest.implementaciones

import tallerwapo.core.dominio.dto.contabilidad.GastoDTO
import tallerwapo.core.dominio.dto.contabilidad.GastoConCitaDTO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.apirest.ApiConfig.BASE_URL
import tallerwapo.core.apirest.ApiRest
import tallerwapo.core.apirest.interfaces.GastosApi

class GastosApiImpl : GastosApi {

    /** Nuevo gasto simple usando DTO */
    override suspend fun guardarNuevoGasto(gasto: GastoDTO): RespuestaDTO<GastoDTO> {
        return ApiRest.post(
            url = BASE_URL + CREAR_NUEVO,
            body = gasto
        )
    }

    /** Nuevo gasto asociado a una cita usando DTO */
    override suspend fun guardarNuevoGasto(gastoConCita: GastoConCitaDTO): RespuestaDTO<GastoConCitaDTO> {
        return ApiRest.post(
            url = BASE_URL + CREAR_NUEVO_CITA,
            body = gastoConCita
        )
    }

    override suspend fun buscarTodos(): RespuestaDTO<List<GastoDTO>> {
        return ApiRest.get(url = BASE_URL + BUSCAR_TODOS)
    }

    override suspend fun buscarPorCita(citaUuid: String): RespuestaDTO<List<GastoDTO>> {
        return ApiRest.get(
            url = BASE_URL + BUSCAR_POR_CITA,
            params = mapOf("citaUuid" to citaUuid)
        )
    }

    override suspend fun actualizarGasto(gasto: GastoDTO): RespuestaDTO<GastoDTO> {
        return ApiRest.post(
            url = BASE_URL + ACTUALIZAR,
            body = gasto
        )
    }

    override suspend fun eliminarGasto(gasto: GastoDTO): RespuestaDTO<GastoDTO> {
        return ApiRest.post(
            url = BASE_URL + ELIMINAR,
            body = gasto
        )
    }
}
