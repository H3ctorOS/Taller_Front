package tallerwapo.core.apirest.interfaces

import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.dto.VehiculoDTO
import tallerwapo.core.dominio.dto.ClienteDTO

interface VehiculosApi {
    val BUSCAR_VEHICULO_MATRICULA_URL: String get() = "/vehiculos/buscarVehiculoPorMatricula"
    val BUSCAR_VEHICULOS_CLIENTE_URL: String get() = "/vehiculos/buscarVehiculosCliente"
    val BUSCARTODOS_VEHICULO_URL: String get() = "/vehiculos/buscarTodosLosVehiculos"
    val CREAR_VEHICULO_URL: String get() = "/vehiculos/crearVehiculo"
    val ELIMIAR_VEHICULO_URL: String get() = "/vehiculos/eliminarVehiculo"
    val ACTUALIZAR_VEHICULO_URL: String get() = "/vehiculos/actualizarVehiculo"

    suspend fun crearVehiculo(vehiculo: VehiculoDTO): RespuestaDTO<VehiculoDTO>
    suspend fun eliminarVehiculo(vehiculo: VehiculoDTO): RespuestaDTO<VehiculoDTO>
    suspend fun actualizarVehiculo(vehiculo: VehiculoDTO): RespuestaDTO<VehiculoDTO>
    suspend fun buscarPorMatricula(matricula: String): RespuestaDTO<VehiculoDTO>
    suspend fun buscarPorCliente(cliente: ClienteDTO): RespuestaDTO<List<VehiculoDTO>>
    suspend fun buscarTodos(): RespuestaDTO<List<VehiculoDTO>>
}
