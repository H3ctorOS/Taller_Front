package tallerwapo.core.dominio.bo

import kotlin.time.Instant
import tallerwapo.core.dominio.bo.interfaz.BaseBO


data class GastoBO(
    override val uuid: Int = 0,
    val descripcion: String = "",
    val importe: Double = 0.0,
    val fecha: Instant,
    val observaciones: String = ""
) : BaseBO