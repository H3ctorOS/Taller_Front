package tallerwapo.core.apirest.interfaces

import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.dto.gestion.servidor.ResumenDatosAppDTO


interface GestionServidorApi {

    val APAGAR: String get() = "/gestion/sistema/apagarEquipo"

    val GET_ESTADO: String get() = "/gestion/sistema/estado"

    suspend fun apagarServidor()
    suspend fun getEstadoServidor(): RespuestaDTO<ResumenDatosAppDTO>
}