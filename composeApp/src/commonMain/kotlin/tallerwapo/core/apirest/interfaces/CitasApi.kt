package tallerwapo.core.apirest.interfaces

import tallerwapo.core.dominio.dto.CitaDTO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.dto.VehiculoDTO

interface CitasApi {

    val CREAR_NUEVA: String get() = "/citas/crearNuevaCita"
    val BUSCAR_TODAS: String get() = "/citas/buscarTodas"
    val BUSCAR_POR_VEHICULO: String get() = "/citas/buscarPorVehiculo"
    val ACTUALIZAR: String get() = "/citas/actualizarCita"
    val ELIMINAR: String get() = "/citas/eliminarCita"

    // Trabaja solo con DTOs
    suspend fun crearCita(cita: CitaDTO): RespuestaDTO<CitaDTO>

    suspend fun buscarTodas(): RespuestaDTO<List<CitaDTO>>

    suspend fun buscarPorVehiculo(vehiculo: VehiculoDTO): RespuestaDTO<List<CitaDTO>>
}