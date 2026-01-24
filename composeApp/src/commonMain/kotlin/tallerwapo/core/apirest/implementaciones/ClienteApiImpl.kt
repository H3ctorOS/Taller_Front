package tallerwapo.core.apirest.implementaciones

import tallerwapo.core.apirest.ApiConfig.BASE_URL
import tallerwapo.core.apirest.ApiRest
import tallerwapo.core.apirest.interfaces.ClienteApi
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.dominio.dto.RespuestaDTO

class ClienteApiImpl() : ClienteApi {

    override suspend fun crearCliente(cliente: ClienteBO): RespuestaDTO<ClienteBO> {

        return ApiRest.post(
            url = BASE_URL + CREAR_CLIENTE_URL,
            body = cliente
        )

    }

    override suspend fun eliminarCliente(clienteId: ClienteBO): RespuestaDTO<ClienteBO> {
        TODO("Not yet implemented")
    }

    override suspend fun buscarTodosLosClientes(): RespuestaDTO<List <ClienteBO>> {
        TODO("Not yet implemented")
    }
}