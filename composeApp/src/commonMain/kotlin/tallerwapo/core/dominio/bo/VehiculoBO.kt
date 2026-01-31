package tallerwapo.core.dominio.bo

import kotlinx.serialization.Serializable
import tallerwapo.core.dominio.bo.interfaz.BaseBO

@Serializable
data class VehiculoBO(
    override var uuid: Int = 0,
    var uuidPropietario: Int = 0,
    var matricula: String = "",
    var marca: String = "",
    var modelo: String = "",
    var estado: String = "",
): BaseBO