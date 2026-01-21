package tallerwapo.core.dominio.repositorio

import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.apirest.interfaces.ClienteApi

class ClientesRepositorio (
    private val apiRest : ClienteApi
) {

    suspend fun crearCliente(cliente: ClienteBO) {
        apiRest.crearCliente(cliente)
    }

}