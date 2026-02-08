package tallerwapo.taller_interfaz.boDeInterfaz

import androidx.compose.runtime.mutableStateListOf
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.GastoBO
import tallerwapo.core.dominio.bo.IngresoBO
import tallerwapo.taller_interfaz.interfaces.BaseBOUI

class CitaBoUI(val cita: CitaBO) : BaseBOUI {
    override val uuid: Int get() = cita.uuid

    // Listas reactivas para Compose
    val ingresos = mutableStateListOf<IngresoBO>().apply { addAll(cita.ingresos) }
    val gastos = mutableStateListOf<GastoBO>().apply { addAll(cita.gastos) }
}