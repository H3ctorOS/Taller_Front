package tallerwapo.core.utils

import tallerwapo.core.dominio.bo.interfaz.BaseBO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.taller_interfaz.emergentes.MensajesEmergentes
import java.util.Objects

object FormulariosService {

    fun  gestionarRespuestaApi(
        respuesta: RespuestaDTO<out BaseBO>,
        accionCerrar: () -> Unit = {}
    ){
        if (respuesta.isOk) {
            MensajesEmergentes.mostrarDialogo(
                titulo = "Todo ok",
                mensaje = respuesta.mensaje,
                botones = listOf(
                    MensajesEmergentes.BotonDialogo("ok") {accionCerrar() },
                )
            )


        }else{
            MensajesEmergentes.mostrarDialogo(
                titulo = "ERRORRRRR",
                mensaje = respuesta.mensaje,
                botones = listOf(
                    MensajesEmergentes.BotonDialogo("Ok") { },
                )
            )
        }

    }


}

