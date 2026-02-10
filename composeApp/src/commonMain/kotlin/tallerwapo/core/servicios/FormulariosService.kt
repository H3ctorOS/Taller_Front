package tallerwapo.core.servicios

import tallerwapo.core.dominio.bo.interfaz.BaseBO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.taller_interfaz.objetos.emergentes.MensajesEmergentes

object FormulariosService {

    fun gestionarRespuestaApi(
        respuesta: RespuestaDTO<out BaseBO>,
        accionCerrar: () -> Unit = {},
        mostrarOk: Boolean = false // <-- debe ir al final
    ) {
        if (respuesta.isOk) {
            if (mostrarOk) { // solo mostrar emergente si mostrarOk = true
                MensajesEmergentes.mostrarDialogo(
                    titulo = "Osss",
                    mensaje = respuesta.mensaje,
                    botones = listOf(
                        MensajesEmergentes.BotonDialogo("Ok") { accionCerrar() },
                    )
                )
            } else {
                // Si no queremos mostrar nada, ejecutamos solo la acción de cierre
                accionCerrar()
            }
        } else {
            // Siempre mostramos el error
            MensajesEmergentes.mostrarDialogo(
                titulo = "ERROR",
                mensaje = respuesta.mensaje,
                botones = listOf(
                    MensajesEmergentes.BotonDialogo("Ok") { },
                )
            )
        }
    }
}
