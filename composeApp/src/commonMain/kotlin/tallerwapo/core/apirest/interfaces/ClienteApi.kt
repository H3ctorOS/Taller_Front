package tallerwapo.core.apirest.interfaces

import tallerwapo.core.dominio.dto.ClienteDTO
import tallerwapo.core.dominio.dto.RespuestaDTO

interface ClienteApi {

    val CREAR_CLIENTE_URL: String get() = "/clientes/crearCliente"
    val BUSCAR_TODOS_CLIENTE_URL: String get() = "/clientes/buscarTodosLosClientes"
    val ELIMIAR_CLIENTE_URL: String get() = "/clientes/eliminarCliente"
    val ACTUALIZAR_CLIENTE_URL: String get() = "/clientes/actualizarCliente"

    suspend fun crearCliente(cliente: ClienteDTO): RespuestaDTO<ClienteDTO>
    suspend fun eliminarCliente(cliente: ClienteDTO): RespuestaDTO<ClienteDTO>
    suspend fun buscarTodosLosClientes(): RespuestaDTO<List<ClienteDTO>>
    suspend fun actualizarCliente(cliente: ClienteDTO): RespuestaDTO<ClienteDTO>
}
