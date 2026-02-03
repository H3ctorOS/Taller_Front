package tallerwapo.core.contexto


import tallerwapo.core.apirest.implementaciones.CitasApiImpl
import tallerwapo.core.apirest.implementaciones.VehiculoApiImpl
import tallerwapo.core.apirest.interfaces.VehiculosApi
import tallerwapo.core.apirest.implementaciones.ClienteApiImpl
import tallerwapo.core.apirest.interfaces.CitasApi
import tallerwapo.core.apirest.interfaces.ClienteApi
import tallerwapo.core.dominio.repositorio.CitasRepositorio
import tallerwapo.core.dominio.repositorio.ClientesRepositorio
import tallerwapo.core.dominio.repositorio.VehiculosRepositorio

object ApiContexto {


    //Implementaciones API
    val vehiculosApi: VehiculosApi by lazy { VehiculoApiImpl() }
    val clientesApi: ClienteApi by lazy { ClienteApiImpl() }
    val citasApi: CitasApi by lazy { CitasApiImpl() }


    //Repositorios
    val clientesRepo: ClientesRepositorio by lazy { ClientesRepositorio(clientesApi) }
    val vehiculosRepo: VehiculosRepositorio by lazy { VehiculosRepositorio(vehiculosApi) }
    val citasRepo: CitasRepositorio by lazy { CitasRepositorio(citasApi) }

}