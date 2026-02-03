package tallerwapo.core.apirest.interfaces

import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.dominio.dto.RespuestaDTO

interface VehiculosApi {
    val BUSCAR_VEHICULO_MATRICULA_URL: String get() = "/vehiculos/buscarVehiculoPorMatricula"
    val BUSCAR_VEHICULOS_CLIENTE_URL: String get() = "/vehiculos/buscarVehiculosCliente"
    val BUSCARTODOS_VEHICULO_URL: String get() = "/vehiculos/buscarTodosLosVehiculos"
    val CREAR_VEHICULO_URL: String get() = "/vehiculos/crearVehiculo"
    val ELIMIAR_VEHICULO_URL: String get() = "/vehiculos/eliminarVehiculo"
    val ACTUALIZAR_VEHICULO_URL: String get() = "/vehiculos/actualizarVehiculo"


    suspend fun crearVehiculo(vehiculo : VehiculoBO) : RespuestaDTO<VehiculoBO>
    suspend fun eliminareVhiculo(vehiculo : VehiculoBO): RespuestaDTO<VehiculoBO>
    suspend fun actualizarVehiculo(vehiculo : VehiculoBO): RespuestaDTO<VehiculoBO>
    suspend fun buscarPorMatricula(matricula : String) : RespuestaDTO<VehiculoBO>
    suspend fun buscarPorCliente(cliente: ClienteBO): RespuestaDTO<List <VehiculoBO>>

    suspend fun buscarTodos(): RespuestaDTO<List <VehiculoBO>>
}