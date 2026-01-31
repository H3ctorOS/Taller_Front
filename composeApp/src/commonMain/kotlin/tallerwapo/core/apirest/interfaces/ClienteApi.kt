package tallerwapo.core.apirest.interfaces

import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.dominio.dto.RespuestaDTO

interface ClienteApi {

    val CREAR_CLIENTE_URL: String get() = "/clientes/crearCliente"
    val BUSCAR_TODOS_CLIENTE_URL: String get() = "/clientes/buscarTodosLosClientes"
    val ELIMIAR_CLIENTE_URL: String get() = "/clientes/eliminarCliente"
    val ACTUALIZAR_CLIENTE_URL: String get() = "/clientes/actualizarCliente"

    suspend fun crearCliente(cliente: ClienteBO): RespuestaDTO<ClienteBO>
    suspend fun eliminarCliente(cliente: ClienteBO): RespuestaDTO<ClienteBO>
    suspend fun buscarTodosLosClientes(): RespuestaDTO<List <ClienteBO>>
    suspend fun actualizarCliente(cliente: ClienteBO): RespuestaDTO<ClienteBO>
}