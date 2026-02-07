package tallerwapo.core.contexto


import tallerwapo.core.apirest.implementaciones.CitasApiImpl
import tallerwapo.core.apirest.implementaciones.VehiculoApiImpl
import tallerwapo.core.apirest.interfaces.VehiculosApi
import tallerwapo.core.apirest.implementaciones.ClienteApiImpl
import tallerwapo.core.apirest.implementaciones.GastosApiImpl
import tallerwapo.core.apirest.implementaciones.IngresosApiImpl
import tallerwapo.core.apirest.interfaces.CitasApi
import tallerwapo.core.apirest.interfaces.ClienteApi
import tallerwapo.core.apirest.interfaces.GastosApi
import tallerwapo.core.apirest.interfaces.IngresosApi
import tallerwapo.core.dominio.repositorio.CitasRepositorio
import tallerwapo.core.dominio.repositorio.ClientesRepositorio
import tallerwapo.core.dominio.repositorio.GastosRepositorio
import tallerwapo.core.dominio.repositorio.IngresosRepositorio
import tallerwapo.core.dominio.repositorio.VehiculosRepositorio

object ApiContexto {


    //Implementaciones API
    val vehiculosApi: VehiculosApi by lazy { VehiculoApiImpl() }
    val clientesApi: ClienteApi by lazy { ClienteApiImpl() }
    val citasApi: CitasApi by lazy { CitasApiImpl() }
    val ingresoApi: IngresosApi by lazy { IngresosApiImpl() }
    val gastosApi: GastosApi by lazy { GastosApiImpl() }


    //Repositorios
    val clientesRepo: ClientesRepositorio by lazy { ClientesRepositorio(clientesApi) }
    val vehiculosRepo: VehiculosRepositorio by lazy { VehiculosRepositorio(vehiculosApi) }
    val citasRepo: CitasRepositorio by lazy { CitasRepositorio(citasApi) }
    val ingresosRepo: IngresosRepositorio by lazy { IngresosRepositorio(ingresoApi) }
    val gastosRepo: GastosRepositorio by lazy { GastosRepositorio(gastosApi) }

}