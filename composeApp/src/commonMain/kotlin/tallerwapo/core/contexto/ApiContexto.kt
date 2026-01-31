package tallerwapo.core.contexto


import tallerwapo.core.apirest.implementaciones.VehiculoApiImpl
import tallerwapo.core.apirest.interfaces.VehiculosApi
import tallerwapo.core.apirest.implementaciones.ClienteApiImpl
import tallerwapo.core.apirest.interfaces.ClienteApi
import tallerwapo.core.dominio.repositorio.ClientesRepositorio
import tallerwapo.core.dominio.repositorio.VehiculosRepositorio

object ApiContexto {


    //Implementaciones de api
    val vehiculosApi: VehiculosApi by lazy { VehiculoApiImpl() }
    val clientesApi: ClienteApi by lazy { ClienteApiImpl() }


    //Repositorios
    val clientesRepo: ClientesRepositorio by lazy { ClientesRepositorio(clientesApi) }

    val vehiculosRepo: VehiculosRepositorio by lazy { VehiculosRepositorio(vehiculosApi) }

}