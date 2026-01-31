package tallerwapo.core.dominio.repositorio


import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.apirest.interfaces.ClienteApi
import tallerwapo.core.dominio.dto.RespuestaDTO

class ClientesRepositorio (
    private val apiRest : ClienteApi
) {

    suspend fun crearCliente(cliente: ClienteBO): RespuestaDTO<ClienteBO> {

        //llamar al servidor por api
        return apiRest.crearCliente(cliente)
    }

    suspend fun buscarTodosLosClientes(): List<ClienteBO>? {

        //llamar al servidor por api
        var respuesta : RespuestaDTO<List <ClienteBO>> = apiRest.buscarTodosLosClientes()
        return respuesta.BoRespuesta;
    }

    suspend fun actualizarCliente(cliente: ClienteBO): RespuestaDTO<ClienteBO>{

        //llamar al servidor por api
        return apiRest.actualizarCliente(cliente)

    }
    suspend fun eliminarCliente(cliente: ClienteBO): RespuestaDTO<ClienteBO> {

        //llamar al servidor por api
        return apiRest.eliminarCliente(cliente)

    }


}
