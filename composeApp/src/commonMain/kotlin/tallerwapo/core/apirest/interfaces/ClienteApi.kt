package tallerwapo.core.apirest.interfaces

import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.dominio.dto.RespuestaDTO

interface ClienteApi {
    val BUSCAR_TODOS_CLIENTES_URL: String get() = "/clientes/buscarTodosLosClientes"
    val CREAR_CLIENTE_URL: String get() = "/clientes/crearCliente"
    val ELIMIAR_CLIENTE_URL: String get() = "/clientes/eliminarCliente"
    val ACTUALIZAR_CLIENTE_URL: String get() = "/clientes/actualizarCliente"

    suspend fun crearCliente(cliente: ClienteBO): RespuestaDTO<ClienteBO>
    suspend fun eliminarCliente(clienteId: ClienteBO): RespuestaDTO<ClienteBO>
    suspend fun buscarTodosLosClientes(): RespuestaDTO<List <ClienteBO>>
}