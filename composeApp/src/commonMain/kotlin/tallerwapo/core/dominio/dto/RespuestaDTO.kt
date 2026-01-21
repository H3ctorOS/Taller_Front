package tallerwapo.core.dominio.dto

import kotlinx.serialization.Serializable

@Serializable
data class RespuestaDTO<T>(
    val status: Int,
    val mensaje: String,
    val isOk: Boolean,
    val objeto: T? = null
) {
    override fun toString(): String {
        return "status=$status, mensaje='$mensaje', isOk=$isOk, objeto=$objeto"}
}