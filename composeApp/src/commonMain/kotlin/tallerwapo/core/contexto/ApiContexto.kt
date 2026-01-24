package tallerwapo.core.contexto


import tallerwapo.core.apirest.implementaciones.VehiculoApiImpl
import tallerwapo.core.apirest.interfaces.VehiculosApi
import tallerwapo.core.apirest.implementaciones.ClienteApiImpl
import tallerwapo.core.apirest.interfaces.ClienteApi

object ApiContexto {


    //Implementaciones
    val vehiculosApi: VehiculosApi by lazy { VehiculoApiImpl() }
    val clientesApi: ClienteApi by lazy { ClienteApiImpl() }

}