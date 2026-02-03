package tallerwapo.core.apirest.interfaces

import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.dto.RespuestaDTO

interface CitasApi {

    val CREAR_NUEVA: String get() = "/citas/crearNuevaCita"

    suspend fun crearCita(cita: CitaBO): RespuestaDTO<CitaBO>

}