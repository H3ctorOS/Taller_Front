package tallerwapo.core.dominio.bo

import kotlinx.serialization.Serializable
import tallerwapo.core.dominio.bo.interfaz.BaseBO
import kotlin.time.Instant


@Serializable
data class CitaBO (
    override val uuid: Int=0,
    val vehiculoUuid: Int,
    val concepto: String= "",
    val fechaInicio: Instant,
    val fechaFinalizada: Instant,
    val codigoEstado: String= "",
    val observaciones: String= ""
): BaseBO{
}