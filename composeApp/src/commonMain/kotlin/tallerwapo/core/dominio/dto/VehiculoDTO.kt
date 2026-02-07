package tallerwapo.core.dominio.dto


import kotlinx.serialization.Serializable
import tallerwapo.core.dominio.bo.VehiculoBO

@Serializable
data class VehiculoDTO(
    val uuid: Int = 0,
    val uuidPropietario: Int = 0,
    val matricula: String = "",
    val marca: String = "",
    val modelo: String = "",
    val estado: String = "",
    val observaciones: String = ""
){
    // Constructor secundario desde BO
    constructor(bo: VehiculoBO) : this(
        uuid = bo.uuid,
        uuidPropietario = bo.uuidPropietario,
        matricula = bo.matricula,
        marca = bo.marca,
        modelo = bo.modelo,
        estado = bo.estado,
        observaciones = bo.observaciones
    )
}