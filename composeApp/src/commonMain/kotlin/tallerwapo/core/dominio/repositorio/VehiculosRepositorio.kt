package tallerwapo.core.dominio.repositorio

import tallerwapo.core.dominio.dto.VehiculoDTO
import tallerwapo.core.dominio.dto.ClienteDTO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.apirest.interfaces.VehiculosApi
import tallerwapo.core.utils.Logs

class VehiculosRepositorio(
    private val apiRest: VehiculosApi
) {

    // Crear vehículo: recibe BO, convierte a DTO, llama API, devuelve BO
    suspend fun crearVehiculo(vehiculo: VehiculoBO): RespuestaDTO<VehiculoBO> {
        Logs.info(this, "Enviando request para crear vehículo")
        val dto = VehiculoDTO(vehiculo)
        val respuestaDTO = apiRest.crearVehiculo(dto)
        val boRespuesta = respuestaDTO.BoRespuesta?.let { VehiculoBO(it) }

        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }

    // Modificar vehículo
    suspend fun modificarVehiculo(vehiculo: VehiculoBO): RespuestaDTO<VehiculoBO> {
        Logs.info(this, "Enviando request para modificar vehículo")
        val dto = VehiculoDTO(vehiculo)
        val respuestaDTO = apiRest.actualizarVehiculo(dto)
        val boRespuesta = respuestaDTO.BoRespuesta?.let { VehiculoBO(it) }

        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }

    // Eliminar vehículo
    suspend fun eliminarVehiculo(vehiculo: VehiculoBO): RespuestaDTO<VehiculoBO> {
        Logs.info(this, "Enviando request para eliminar vehículo")
        val dto = VehiculoDTO(vehiculo)
        val respuestaDTO = apiRest.eliminarVehiculo(dto)
        val boRespuesta = respuestaDTO.BoRespuesta?.let { VehiculoBO(it) }

        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }

    // Buscar vehículos por cliente
    suspend fun buscarPorCliente(cliente: ClienteBO): List<VehiculoBO> {
        Logs.info(this, "Buscando vehículos del cliente: $cliente")

        // Convertir BO → DTO
        val clienteDTO = ClienteDTO(cliente)

        // Llamada a la API (devuelve DTOs)
        val respuestaDTO = apiRest.buscarPorCliente(clienteDTO)

        // Convertir lista de DTOs → lista de BOs
        val boList = respuestaDTO.BoRespuesta?.map { VehiculoBO(it) } ?: emptyList()

        Logs.info(this, "Cantidad de vehículos recibidos: ${boList.size}")
        return boList
    }

    // Buscar por matrícula
    suspend fun buscarPorMatricula(matricula: String): RespuestaDTO<VehiculoBO> {
        Logs.info(this, "Buscando vehículo por matrícula: $matricula")
        val respuestaDTO = apiRest.buscarPorMatricula(matricula)
        val boRespuesta = respuestaDTO.BoRespuesta?.let { VehiculoBO(it) }

        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }

    // Buscar todos los vehículos
    suspend fun buscarTodos(): List<VehiculoBO> {
        Logs.info(this, "Buscando todos los vehículos")

        // Llamada a la API (devuelve DTOs)
        val respuestaDTO = apiRest.buscarTodos()

        // Convertir lista de DTOs → lista de BOs
        val boList = respuestaDTO.BoRespuesta?.map { VehiculoBO(it) } ?: emptyList()

        Logs.info(this, "Cantidad de vehículos recibidos: ${boList.size}")
        return boList
    }

}
