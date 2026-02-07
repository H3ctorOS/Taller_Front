package tallerwapo.core.dominio.bo

import kotlin.time.Instant
import tallerwapo.core.dominio.bo.interfaz.BaseBO


data class IngresoBO(
    override val uuid: Int = 0,
    val concepto: String = "",
    val importe: Double = 0.0,
    val fecha: Instant,
    val codEstado: String = "",
    val observaciones: String = ""
) : BaseBO