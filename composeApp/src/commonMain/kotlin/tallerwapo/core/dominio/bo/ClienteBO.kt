package tallerwapo.core.dominio.bo

import kotlinx.serialization.Serializable
import tallerwapo.core.dominio.bo.interfaz.BaseBO

@Serializable
data class ClienteBO(
    override val uuid: Int= 0,
    val dni: String= "",
    val nombre: String= "",
    val apellidos: String= "",
    val direccion: String= "",
    val telefono: Int= 0,
    val email: String= "",
    val estado: String= "",
): BaseBO