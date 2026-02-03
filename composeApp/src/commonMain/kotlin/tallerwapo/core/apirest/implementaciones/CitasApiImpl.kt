package tallerwapo.core.apirest.implementaciones

import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.apirest.ApiConfig.BASE_URL
import tallerwapo.core.apirest.ApiRest
import tallerwapo.core.apirest.interfaces.CitasApi
import tallerwapo.core.dominio.bo.CitaBO


class CitasApiImpl(): CitasApi {

    override suspend fun crearCita(cita: CitaBO): RespuestaDTO<CitaBO> {
        return ApiRest.post(
            url = BASE_URL + CREAR_NUEVA,
            body = cita
        )
    }

}