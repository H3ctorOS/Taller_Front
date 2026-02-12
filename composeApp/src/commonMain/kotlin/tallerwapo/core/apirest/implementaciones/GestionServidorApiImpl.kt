package tallerwapo.core.apirest.implementaciones

import tallerwapo.core.apirest.ApiConfig.BASE_URL
import tallerwapo.core.apirest.ApiRest
import tallerwapo.core.apirest.interfaces.GestionServidorApi
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.dto.calendario.SemanasDelAnioDTO
import tallerwapo.core.dominio.dto.gestion.servidor.ResumenDatosAppDTO


class GestionServidorApiImpl : GestionServidorApi {

    override suspend fun apagarServidor() {
        ApiRest.post(
            url = BASE_URL + APAGAR,
            body = ""
        )
    }

    override suspend fun getEstadoServidor(): RespuestaDTO<ResumenDatosAppDTO> {
        return ApiRest.get(url = BASE_URL + GET_ESTADO)
    }

    override suspend fun getSemanasAnio(anio: Int): SemanasDelAnioDTO? {
        var respuesta : RespuestaDTO<SemanasDelAnioDTO> = ApiRest.get(
            url = BASE_URL + GET_SEMANAS_ANIO,
            params = mapOf("anio" to anio)
        )
        return respuesta.BoRespuesta
    }

}
