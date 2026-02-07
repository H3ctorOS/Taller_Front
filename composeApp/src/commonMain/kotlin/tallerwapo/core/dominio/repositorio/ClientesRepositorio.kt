package tallerwapo.core.dominio.repositorio

import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.dominio.dto.ClienteDTO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.apirest.interfaces.ClienteApi
import tallerwapo.core.utils.Logs

class ClientesRepositorio(
    private val apiRest: ClienteApi
) {

    // Crear cliente
    suspend fun crearCliente(cliente: ClienteBO): RespuestaDTO<ClienteBO> {
        Logs.info(this, "Creando cliente: $cliente")
        val dto = ClienteDTO(cliente) // BO -> DTO
        val respuestaDTO = apiRest.crearCliente(dto)
        val boRespuesta = respuestaDTO.BoRespuesta?.let { ClienteBO(it) }

        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }

    // Actualizar cliente
    suspend fun actualizarCliente(cliente: ClienteBO): RespuestaDTO<ClienteBO> {
        Logs.info(this, "Actualizando cliente: $cliente")
        val dto = ClienteDTO(cliente)
        val respuestaDTO = apiRest.actualizarCliente(dto)
        val boRespuesta = respuestaDTO.BoRespuesta?.let { ClienteBO(it) }

        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }

    // Eliminar cliente
    suspend fun eliminarCliente(cliente: ClienteBO): RespuestaDTO<ClienteBO> {
        Logs.info(this, "Eliminando cliente: $cliente")
        val dto = ClienteDTO(cliente)
        val respuestaDTO = apiRest.eliminarCliente(dto)
        val boRespuesta = respuestaDTO.BoRespuesta?.let { ClienteBO(it) }

        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }

    // Buscar todos los clientes
    suspend fun buscarTodos(): List<ClienteBO> {
        Logs.info(this, "Buscando todos los clientes")

        // Llamada a la API (devuelve DTOs)
        val respuestaDTO = apiRest.buscarTodosLosClientes()

        // Convertir lista de DTOs → lista de BOs
        val boList = respuestaDTO.BoRespuesta?.map { ClienteBO(it) } ?: emptyList()

        Logs.info(this, "Cantidad de clientes recibidos: ${boList.size}")
        return boList
    }

}
