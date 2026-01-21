package tallerwapo.core.dominio.repositorio


import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.apirest.interfaces.VehiculosApi
import tallerwapo.core.utils.Logs

class VehiculosRepositorio  (
    private val apiRest : VehiculosApi
) {

    suspend fun crearVehiculo(vehiculo : VehiculoBO) : RespuestaDTO <VehiculoBO>{
        Logs.info(this, "Enviando request para crear vehículo")
        val respuesta =  apiRest.crearVehiculo(vehiculo)
        return respuesta
    }

}