package tallerwapo.core.apirest.interfaces

import io.ktor.http.cio.Response
import tallerwapo.core.dominio.bo.ClienteBO

interface ClienteApi {
    val BUSCAR_TODOS_CLIENTES_URL: String get() = "/clientes/buscarTodosLosClientes"
    val CREAR_CLIENTE_URL: String get() = "/clientes/crearCliente"
    val ELIMIAR_CLIENTE_URL: String get() = "/clientes/eliminarCliente"
    val ACTUALIZAR_CLIENTE_URL: String get() = "/clientes/actualizarCliente"

    suspend fun crearCliente(cliente: ClienteBO): Response
    suspend fun eliminarCliente(clienteId: ClienteBO): Response
    suspend fun buscarTodosLosClientes(): Response
}