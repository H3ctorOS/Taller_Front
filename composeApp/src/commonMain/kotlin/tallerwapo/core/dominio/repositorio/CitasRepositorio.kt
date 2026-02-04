package tallerwapo.core.dominio.repositorio


import tallerwapo.core.apirest.interfaces.CitasApi
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.utils.Logs

class CitasRepositorio (
    private val apiRest : CitasApi
) {

    suspend fun crearCita(cita: CitaBO): RespuestaDTO<CitaBO>  {
        //llamar al servidor por api
        Logs.info(this, "Creando nueva cita: $cita")
        val respuesta = apiRest.crearCita(cita)
        Logs.info(this, "Respuesta al crear la citra: ${respuesta.mensaje}")
        return respuesta
    }


    suspend fun buscarTodas(): RespuestaDTO <List<CitaBO>>  {
        Logs.info(this, "Buscando todas las citas")
        val respuesta = apiRest.buscarTodas()
        apiRest.buscarTodas()
        Logs.info(this, "Cantidad de citas recibidas: " + respuesta.BoRespuesta?.size)
        return respuesta
    }


    suspend fun buscarPorVehiculo(vehiculoBO: VehiculoBO): RespuestaDTO <List<CitaBO>>  {
        Logs.info(this, "Buscando citas del vehiculo: $vehiculoBO")
        val respuesta = apiRest.buscarPorVehiculo(vehiculoBO)
        Logs.info(this, "Cantidad de citas recibidas: " + respuesta.BoRespuesta?.size)
        return respuesta
    }

}
