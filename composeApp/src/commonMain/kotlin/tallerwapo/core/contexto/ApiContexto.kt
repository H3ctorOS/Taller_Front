package tallerwapo.core.contexto

import io.ktor.client.HttpClient
import tallerwapo.core.apirest.implementaciones.VehiculoApiImpl
import tallerwapo.core.apirest.interfaces.VehiculosApi
import tallerwapo.core.apirest.HttpClientProvider

object ApiContexto {


    //Implementaciones
    val vehiculosApi: VehiculosApi by lazy {VehiculoApiImpl()}

}