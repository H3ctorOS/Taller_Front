package tallerwapo.taller_interfaz.objetos.listables.interfaz

import tallerwapo.core.dominio.bo.interfaz.BaseBO

interface ListableBO<T : BaseBO> {
    val bo: T

    val id: String get() = bo.uuid.toString()
    val titulo: String
    val subtitulo: String?
    val descripcion: String?
}