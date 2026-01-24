package tallerwapo.core.dominio.bo

import kotlinx.serialization.Serializable
import tallerwapo.core.dominio.bo.interfaz.BaseBO

@Serializable
data class ClienteBO(
    val uuid: Int,
    val dni: String,
    val nombre: String,
    val apellidos: String,
    val direccion: String,
    val telefono: Int,
    val email: String,
    val estado: String
): BaseBO