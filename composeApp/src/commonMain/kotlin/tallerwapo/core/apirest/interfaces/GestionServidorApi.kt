package tallerwapo.core.apirest.interfaces

import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.dto.calendario.SemanasDelAnioDTO
import tallerwapo.core.dominio.dto.gestion.servidor.ResumenDatosAppDTO


interface GestionServidorApi {

    val APAGAR: String get() = "/gestion/sistema/apagarEquipo"

    val GET_ESTADO: String get() = "/gestion/sistema/estado"
    val GET_SEMANAS_ANIO: String get() = "/gestion/sistema/semanasDelAnio"

    suspend fun apagarServidor()
    suspend fun getEstadoServidor(): RespuestaDTO<ResumenDatosAppDTO>
    suspend fun getSemanasAnio(anio: Int): SemanasDelAnioDTO?
}