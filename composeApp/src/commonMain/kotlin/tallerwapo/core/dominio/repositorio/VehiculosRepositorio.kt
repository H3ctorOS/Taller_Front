package tallerwapo.core.dominio.repositorio


import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.apirest.interfaces.VehiculosApi
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.utils.Logs

class VehiculosRepositorio  (
    private val apiRest : VehiculosApi
) {

    suspend fun crearVehiculo(vehiculo : VehiculoBO) : RespuestaDTO <VehiculoBO>{
        Logs.info(this, "Enviando request para crear vehículo")
        return  apiRest.crearVehiculo(vehiculo)
    }


    suspend fun buscarPorCliente(cliente : ClienteBO) : List<VehiculoBO>? {

        //llamar al servidor por api
        var respuesta : RespuestaDTO<List <VehiculoBO>> = apiRest.buscarPorCliente(cliente)
        return respuesta.BoRespuesta;
    }

}