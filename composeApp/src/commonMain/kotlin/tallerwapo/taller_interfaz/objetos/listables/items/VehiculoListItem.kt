package tallerwapo.taller_interfaz.objetos.listables.items

import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.taller_interfaz.objetos.listables.interfaz.ListableBO

data class VehiculoListItem(
    override val bo: VehiculoBO
) : ListableBO<VehiculoBO> {

    override val titulo: String = "${bo.marca} ${bo.modelo}"
    override val subtitulo: String = bo.matricula
    override val descripcion: String? = null
}