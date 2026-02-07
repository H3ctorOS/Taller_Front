package tallerwapo.core.apirest.implementaciones

import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.apirest.ApiConfig.BASE_URL
import tallerwapo.core.apirest.ApiRest
import tallerwapo.core.apirest.interfaces.IngresosApi
import tallerwapo.core.dominio.dto.contabilidad.IngresoConCitaDTO
import tallerwapo.core.dominio.dto.contabilidad.IngresoDTO

class IngresosApiImpl : IngresosApi {

    override suspend fun guardarNuevoIngreso(ingreso: IngresoDTO): RespuestaDTO<IngresoDTO> {
        return ApiRest.post(
            url = BASE_URL + CREAR_NUEVO,
            body = ingreso
        )
    }

    override suspend fun guardarNuevoIngreso(ingresoConCita: IngresoConCitaDTO): RespuestaDTO<IngresoConCitaDTO> {
        return ApiRest.post(
            url = BASE_URL + CREAR_NUEVO_CITA,
            body = ingresoConCita
        )
    }

    override suspend fun buscarTodos(): RespuestaDTO<List<IngresoDTO>> {
        return ApiRest.get(url = BASE_URL + BUSCAR_TODOS)
    }

    override suspend fun buscarPorCita(citaUuid: String): RespuestaDTO<List<IngresoDTO>> {
        return ApiRest.get(
            url = BASE_URL + BUSCAR_POR_CITA,
            params = mapOf("citaUuid" to citaUuid)
        )
    }

    override suspend fun actualizarIngreso(ingreso: IngresoDTO): RespuestaDTO<IngresoDTO> {
        return ApiRest.post(
            url = BASE_URL + ACTUALIZAR,
            body = ingreso
        )
    }

    override suspend fun eliminarIngreso(ingreso: IngresoDTO): RespuestaDTO<IngresoDTO> {
        return ApiRest.post(
            url = BASE_URL + ELIMINAR,
            body = ingreso
        )
    }
}
