package tallerwapo.core.apirest.interfaces

import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.dominio.dto.RespuestaDTO

interface CitasApi {

    val CREAR_NUEVA: String get() = "/citas/crearNuevaCita"
    val BUSCAR_TODAS: String get() = "/citas/buscarTodas"
    val BUSCAR_POR_VEHICULO: String get() = "/citas/buscarPorVehiculo"
    val ACTUALIZAR: String get() = "/citas/actualizarCita"
    val ELIMINAR: String get() = "/citas/eliminarCita"

    suspend fun crearCita(cita: CitaBO): RespuestaDTO<CitaBO>

    suspend fun buscarTodas(): RespuestaDTO <List<CitaBO>>

    suspend fun buscarPorVehiculo(vehiculoBO: VehiculoBO): RespuestaDTO <List<CitaBO>>

}