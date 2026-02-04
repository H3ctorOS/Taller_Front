package tallerwapo.core.dominio.dto



@kotlinx.serialization.Serializable
data class CitaDTO(
    val uuid: Int,
    val vehiculoUuid: Int,
    val concepto: String,
    val fechaInicio: Long,
    val fechaFinalizada: Long?,
    val codigoEstado: String,
    val observaciones: String?
)
