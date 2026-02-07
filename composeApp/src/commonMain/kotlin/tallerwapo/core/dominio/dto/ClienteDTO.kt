package tallerwapo.core.dominio.dto

import kotlinx.serialization.Serializable
import tallerwapo.core.dominio.bo.ClienteBO

@Serializable
data class ClienteDTO(
    val uuid: Int = 0,
    val dni: String = "",
    val nombre: String = "",
    val apellidos: String = "",
    val direccion: String = "",
    val telefono: Int = 0,
    val email: String = "",
    val estado: String = "",
    val observaciones: String = ""
) {
    // Constructor secundario desde BO
    constructor(bo: ClienteBO) : this(
        uuid = bo.uuid,
        dni = bo.dni,
        nombre = bo.nombre,
        apellidos = bo.apellidos,
        direccion = bo.direccion,
        telefono = bo.telefono,
        email = bo.email,
        estado = bo.estado,
        observaciones = bo.observaciones
    )
}