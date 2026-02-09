package tallerwapo.core.apirest

import tallerwapo.core.servicios.ConfigServices

object ApiConfig {

    private const val PUERTO = 8080
    private const val RUTA_API = "/api"

    // URL completa dinámica según el valor de IP en ConfigServices
    val BASE_URL: String
        get() = "http://${ConfigServices.obtenerIp()}:$PUERTO$RUTA_API"
}