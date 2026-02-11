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
     * Contenido desplegable opcional que se muestra
     * solo cuando la card está expandida.
     */
    @Composable
    fun ContenidoDesplegable() {
        // vacío por defecto
    }

    /**
     * Contenido extra opcional que se muestra siempre
     * a la derecha de la card.
     */
    val contenidoExtra: (@Composable () -> Unit)?
        get() = null

    @Composable
    fun ContenidoExtra() {
        contenidoExtra?.invoke()
    }
}