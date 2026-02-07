package tallerwapo.core.dominio.repositorio

import tallerwapo.core.apirest.interfaces.IngresosApi
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.IngresoBO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.dto.contabilidad.IngresoDTO
import tallerwapo.core.dominio.dto.contabilidad.IngresoConCitaDTO
import tallerwapo.core.servicios.DtoService
import tallerwapo.core.utils.Logs

class IngresosRepositorio(
    private val apiRest: IngresosApi
) {


    suspend fun crearIngreso(ingreso: IngresoBO): RespuestaDTO<IngresoBO> {
        Logs.info(this, "Creando nuevo ingreso: $ingreso")
        val ingresoDTO = DtoService.toDTO(ingreso)
        val respuestaDTO: RespuestaDTO<IngresoDTO> = apiRest.guardarNuevoIngreso(ingresoDTO)
        val boRespuesta = respuestaDTO.BoRespuesta?.let { DtoService.toBO(it) }
        Logs.info(this, "Respuesta al crear el ingreso: ${respuestaDTO.mensaje}")
        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }


    suspend fun crearIngreso(ingreso: IngresoBO, cita: CitaBO): RespuestaDTO<IngresoBO> {
        Logs.info(this, "Creando ingreso asociado a cita: $ingreso -> ${cita.uuid}")

        val ingresoConCitaDTO = IngresoConCitaDTO(
            ingreso = DtoService.toDTO(ingreso),
            cita = DtoService.toDTO(cita)
        )

        val respuestaDTO: RespuestaDTO<IngresoConCitaDTO> = apiRest.guardarNuevoIngreso(ingresoConCitaDTO)

        val DtoRespuesta = respuestaDTO.BoRespuesta?.let { (it) }
        Logs.info(this, "Respuesta al crear el ingreso con cita: ${respuestaDTO.mensaje}")


        //COMO SOLO SE DEVUELVE LA RESPUESTA CON EL INGRESO HAY QUE HACER UNA MIERDA DE CONVERSION
        // Convertimos el ingreso devuelto de DTO a BO
        val ingresoBO = respuestaDTO.BoRespuesta?.ingreso?.let { DtoService.toBO(it) }

        // Construimos la respuesta final con BO
        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = ingresoBO,
            isOk = respuestaDTO.isOk
        )

    }

    suspend fun actualizarIngreso(ingreso: IngresoBO): RespuestaDTO<IngresoBO> {
        Logs.info(this, "Actualizando ingreso: $ingreso")
        val ingresoDTO = DtoService.toDTO(ingreso)
        val respuestaDTO = apiRest.actualizarIngreso(ingresoDTO)
        val boRespuesta = respuestaDTO.BoRespuesta?.let { DtoService.toBO(it) }
        Logs.info(this, "Respuesta al actualizar el ingreso: ${respuestaDTO.mensaje}")
        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }

    suspend fun eliminarIngreso(ingreso: IngresoBO): RespuestaDTO<IngresoBO> {
        Logs.info(this, "Eliminando ingreso: $ingreso")
        val ingresoDTO = DtoService.toDTO(ingreso)
        val respuestaDTO = apiRest.eliminarIngreso(ingresoDTO)
        val boRespuesta = respuestaDTO.BoRespuesta?.let { DtoService.toBO(it) }
        Logs.info(this, "Respuesta al eliminar el ingreso: ${respuestaDTO.mensaje}")
        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }

    suspend fun buscarTodos(): RespuestaDTO<List<IngresoBO>> {
        Logs.info(this, "Buscando todos los ingresos")
        val respuestaDTO = apiRest.buscarTodos()
        val boList = respuestaDTO.BoRespuesta?.map { DtoService.toBO(it) } ?: emptyList()
        Logs.info(this, "Cantidad de ingresos recibidos: ${boList.size}")
        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boList,
            isOk = respuestaDTO.isOk
        )
    }

    suspend fun buscarPorCita(citaUuid: String): RespuestaDTO<List<IngresoBO>> {
        Logs.info(this, "Buscando ingresos para la cita uuid: $citaUuid")
        val respuestaDTO = apiRest.buscarPorCita(citaUuid)
        val boList = respuestaDTO.BoRespuesta?.map { DtoService.toBO(it) } ?: emptyList()
        Logs.info(this, "Cantidad de ingresos recibidos: ${boList.size}")
        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boList,
            isOk = respuestaDTO.isOk
        )
    }
}
