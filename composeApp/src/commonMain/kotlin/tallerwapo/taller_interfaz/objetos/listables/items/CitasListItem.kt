package tallerwapo.taller_interfaz.objetos.listables.items

import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.taller_interfaz.objetos.listables.interfaz.ListableBO

data class CitasListItem(
    override val bo: CitaBO
) : ListableBO<CitaBO> {

    override val titulo: String = bo.concepto
    override val subtitulo: String = bo.fechaInicio.toString()
    override val descripcion: String? = bo.observaciones
}