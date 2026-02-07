package tallerwapo.core.dominio.repositorio

import tallerwapo.core.apirest.interfaces.GastosApi
import tallerwapo.core.dominio.bo.GastoBO
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.dto.contabilidad.GastoDTO
import tallerwapo.core.dominio.dto.contabilidad.GastoConCitaDTO
import tallerwapo.core.servicios.DtoService
import tallerwapo.core.utils.Logs

class GastosRepositorio(
    private val apiRest: GastosApi
) {

    /** Crear un gasto simple */
    suspend fun crearGasto(gasto: GastoBO): RespuestaDTO<GastoBO> {
        Logs.info(this, "Creando nuevo gasto: $gasto")
        val gastoDTO: GastoDTO = DtoService.toDTO(gasto)
        val respuestaDTO = apiRest.guardarNuevoGasto(gastoDTO)
        Logs.info(this, "Respuesta al crear el gasto: ${respuestaDTO.mensaje}")

        val boRespuesta = respuestaDTO.BoRespuesta?.let { DtoService.toBO(it) }
        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }

    /** Crear un gasto asociado a una cita */
    suspend fun crearGasto(gasto: GastoBO, cita: CitaBO): RespuestaDTO<GastoBO> {
        Logs.info(this, "Creando gasto asociado a cita: $gasto -> ${cita.uuid}")
        val gastoConCitaDTO = GastoConCitaDTO(
            gasto = DtoService.toDTO(gasto),
            cita = DtoService.toDTO(cita)
        )

        val respuestaDTO = apiRest.guardarNuevoGasto(gastoConCitaDTO)
        Logs.info(this, "Respuesta al crear el gasto con cita: ${respuestaDTO.mensaje}")

        val boRespuesta = respuestaDTO.BoRespuesta?.let { DtoService.toBO(it.gasto) }
        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }

    /** Actualizar un gasto */
    suspend fun actualizarGasto(gasto: GastoBO): RespuestaDTO<GastoBO> {
        Logs.info(this, "Actualizando gasto: $gasto")
        val gastoDTO = DtoService.toDTO(gasto)
        val respuestaDTO = apiRest.actualizarGasto(gastoDTO)
        Logs.info(this, "Respuesta al actualizar el gasto: ${respuestaDTO.mensaje}")

        val boRespuesta = respuestaDTO.BoRespuesta?.let { DtoService.toBO(it) }
        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }

    /** Eliminar un gasto */
    suspend fun eliminarGasto(gasto: GastoBO): RespuestaDTO<GastoBO> {
        Logs.info(this, "Eliminando gasto: $gasto")
        val gastoDTO = DtoService.toDTO(gasto)
        val respuestaDTO = apiRest.eliminarGasto(gastoDTO)
        Logs.info(this, "Respuesta al eliminar el gasto: ${respuestaDTO.mensaje}")

        val boRespuesta = respuestaDTO.BoRespuesta?.let { DtoService.toBO(it) }
        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )
    }

    /** Buscar todos los gastos */
    suspend fun buscarTodos(): RespuestaDTO<List<GastoBO>> {
        Logs.info(this, "Buscando todos los gastos")
        val respuestaDTO = apiRest.buscarTodos()
        Logs.info(this, "Cantidad de gastos recibidos: ${respuestaDTO.BoRespuesta?.size}")

        val boList = respuestaDTO.BoRespuesta?.map { DtoService.toBO(it) } ?: emptyList()
        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boList,
            isOk = respuestaDTO.isOk
        )
    }

    /** Buscar gastos por cita */
    suspend fun buscarPorCita(citaUuid: String): RespuestaDTO<List<GastoBO>> {
        Logs.info(this, "Buscando gastos para la cita uuid: $citaUuid")
        val respuestaDTO = apiRest.buscarPorCita(citaUuid)
        Logs.info(this, "Cantidad de gastos recibidos: ${respuestaDTO.BoRespuesta?.size}")

        val boList = respuestaDTO.BoRespuesta?.map { DtoService.toBO(it) } ?: emptyList()
        return RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boList,
            isOk = respuestaDTO.isOk
        )
    }
}
