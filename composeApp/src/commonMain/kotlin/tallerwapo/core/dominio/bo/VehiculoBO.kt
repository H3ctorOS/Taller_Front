package tallerwapo.core.dominio.bo


import tallerwapo.core.dominio.bo.interfaz.BaseBO
import tallerwapo.core.dominio.dto.VehiculoDTO

data class VehiculoBO(
    override var uuid: Int = 0,
    var uuidPropietario: Int = 0,
    var matricula: String = "",
    var marca: String = "",
    var modelo: String = "",
    var estado: String = "",
    val observaciones: String= ""
): BaseBO{

    // Constructor secundario que recibe un DTO
    constructor(dto: VehiculoDTO) : this(
        uuid = dto.uuid,
        uuidPropietario = dto.uuidPropietario,
        matricula = dto.matricula,
        marca = dto.marca,
        modelo = dto.modelo,
        estado = dto.estado,
        observaciones = dto.observaciones
    )

    // Opcional: función de extensión estática para construir desde DTO
    companion object {
        fun fromDTO(dto: VehiculoDTO) = VehiculoBO(dto)
    }
}