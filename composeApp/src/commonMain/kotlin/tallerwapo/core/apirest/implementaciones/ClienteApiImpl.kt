package tallerwapo.core.apirest.implementaciones

import io.ktor.client.HttpClient
import io.ktor.http.cio.Response
import tallerwapo.core.apirest.interfaces.ClienteApi
import tallerwapo.core.dominio.bo.ClienteBO

class ClienteApiImpl(private val httpServer: HttpClient) : ClienteApi {

    override suspend fun crearCliente(cliente: ClienteBO): Response {
        TODO("Not yet implemented")
    }

    override suspend fun eliminarCliente(clienteId: ClienteBO): Response {
        TODO("Not yet implemented")
    }

    override suspend fun buscarTodosLosClientes(): Response {
        TODO("Not yet implemented")
    }
}