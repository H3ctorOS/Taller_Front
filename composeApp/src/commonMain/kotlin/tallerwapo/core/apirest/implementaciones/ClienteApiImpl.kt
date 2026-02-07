package tallerwapo.core.apirest.implementaciones

import tallerwapo.core.apirest.ApiConfig.BASE_URL
import tallerwapo.core.apirest.ApiRest
import tallerwapo.core.apirest.interfaces.ClienteApi
import tallerwapo.core.dominio.dto.ClienteDTO
import tallerwapo.core.dominio.dto.RespuestaDTO

class ClienteApiImpl : ClienteApi {

    override suspend fun crearCliente(cliente: ClienteDTO): RespuestaDTO<ClienteDTO> {
        return ApiRest.post(
            url = BASE_URL + CREAR_CLIENTE_URL,
            body = cliente
        )
    }

    override suspend fun eliminarCliente(cliente: ClienteDTO): RespuestaDTO<ClienteDTO> {
        return ApiRest.post(
            url = BASE_URL + ELIMIAR_CLIENTE_URL,
            body = cliente
        )
    }

    override suspend fun buscarTodosLosClientes(): RespuestaDTO<List<ClienteDTO>> {
        return ApiRest.get(url = BASE_URL + BUSCAR_TODOS_CLIENTE_URL)
    }

    override suspend fun actualizarCliente(cliente: ClienteDTO): RespuestaDTO<ClienteDTO> {
        return ApiRest.post(
            url = BASE_URL + ACTUALIZAR_CLIENTE_URL,
            body = cliente
        )
    }
}
