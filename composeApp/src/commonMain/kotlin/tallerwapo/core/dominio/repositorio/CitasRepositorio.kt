package tallerwapo.core.dominio.repositorio


import tallerwapo.core.apirest.interfaces.CitasApi
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.dto.RespuestaDTO

class CitasRepositorio (
    private val apiRest : CitasApi
) {

    suspend fun crearCita(cita: CitaBO): RespuestaDTO<CitaBO>  {
        //llamar al servidor por api
        return apiRest.crearCita(cita)
    }

}
