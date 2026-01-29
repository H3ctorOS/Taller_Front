package tallerwapo.taller_interfaz.objetos.listables.items

import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.taller_interfaz.objetos.listables.interfaz.ListableBO

data class ClientesListItem(
    override val bo: ClienteBO
) : ListableBO<ClienteBO> {

    override val titulo: String = "${bo.nombre} ${bo.apellidos}"
    override val subtitulo: String = bo.telefono.toString()
    override val descripcion: String? = null
}