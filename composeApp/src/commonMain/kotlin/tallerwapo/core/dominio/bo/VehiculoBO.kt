package tallerwapo.core.dominio.bo

import kotlinx.serialization.Serializable

@Serializable
data class VehiculoBO(
    var uuid: Int = 0,
    var uuidPropietario: Int = 0,
    var matricula: String = "",
    var marca: String = "",
    var modelo: String = "",
    var codigoEstado: String = ""
)