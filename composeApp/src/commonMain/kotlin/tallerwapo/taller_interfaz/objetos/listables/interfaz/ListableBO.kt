package tallerwapo.taller_interfaz.objetos.listables.interfaz

import androidx.compose.runtime.Composable
import tallerwapo.core.dominio.bo.interfaz.BaseBO

interface ListableBO<T : BaseBO> {
    val bo: T

    val id: String get() = bo.uuid.toString()
    val titulo: String
    val subtitulo: String?
    val descripcion: String?

    /**
     * Contenido desplegable opcional que se mostrará
     * debajo de la card cuando esté expandida.
     * Por defecto no se muestra nada.
     */
    @Composable
    fun ContenidoDesplegable() {
        // vacío por defecto
    }
}