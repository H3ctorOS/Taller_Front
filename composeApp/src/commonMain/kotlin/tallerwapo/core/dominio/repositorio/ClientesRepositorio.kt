package tallerwapo.core.dominio.repositorio


import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.apirest.interfaces.ClienteApi
import tallerwapo.core.dominio.dto.RespuestaDTO

class ClientesRepositorio (
    private val apiRest : ClienteApi
) {

    suspend fun crearCliente(cliente: ClienteBO) {
        apiRest.crearCliente(cliente)
    }

    suspend fun buscarTodosLosClientes(): List<ClienteBO>? {
        var respuesta : RespuestaDTO<List <ClienteBO>> = apiRest.buscarTodosLosClientes()
        return respuesta.BoRespuesta;
    }



}
