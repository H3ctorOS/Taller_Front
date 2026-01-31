package tallerwapo.core.apirest.implementaciones

import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.apirest.ApiConfig.BASE_URL
import tallerwapo.core.apirest.ApiRest
import tallerwapo.core.apirest.interfaces.VehiculosApi
import tallerwapo.core.dominio.bo.ClienteBO

class VehiculoApiImpl(): VehiculosApi {

    override suspend fun crearVehiculo(vehiculo: VehiculoBO): RespuestaDTO<VehiculoBO>  {

        return ApiRest.post(
            url = BASE_URL + CREAR_VEHICULO_URL,
            body = vehiculo
        )
    }

    override suspend fun eliminareVhiculo(vehiculo: VehiculoBO): RespuestaDTO<VehiculoBO> {
        TODO("Not yet implemented")
    }

    override suspend fun actualizarVehiculo(vehiculo: VehiculoBO): RespuestaDTO<VehiculoBO>  {
        TODO("Not yet implemented")
    }

    override suspend fun buscarPorMatricula(matricula: String): RespuestaDTO<VehiculoBO>  {
        TODO("Not yet implemented")
    }

    override suspend fun buscarPorCliente(cliente: ClienteBO):  RespuestaDTO<List <VehiculoBO>> {

        return ApiRest.get(url = BASE_URL + BUSCAR_VEHICULOS_CLIENTE_URL
                            ,params = mapOf(
                                    "clienteUuid" to cliente.uuid
                            )
        )

    }
}