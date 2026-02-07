package tallerwapo.core.dominio.bo


import tallerwapo.core.dominio.bo.interfaz.BaseBO
import tallerwapo.core.dominio.dto.ClienteDTO

data class ClienteBO(
    override val uuid: Int= 0,
    val dni: String= "",
    val nombre: String= "",
    val apellidos: String= "",
    val direccion: String= "",
    val telefono: Int= 0,
    val email: String= "",
    val estado: String= "",
    val observaciones: String= ""
): BaseBO{

    // Constructor secundario desde DTO
    constructor(dto: ClienteDTO) : this(
        uuid = dto.uuid,
        dni = dto.dni ?: "",
        nombre = dto.nombre ?: "",
        apellidos = dto.apellidos ?: "",
        direccion = dto.direccion ?: "",
        telefono = dto.telefono ?: 0,
        email = dto.email ?: "",
        estado = dto.estado ?: "",
        observaciones = dto.observaciones ?: ""
    )
}