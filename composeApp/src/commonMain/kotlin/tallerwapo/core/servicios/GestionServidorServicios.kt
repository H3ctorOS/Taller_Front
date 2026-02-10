package tallerwapo.core.servicios

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.AppContexto
import tallerwapo.core.dominio.dto.gestion.servidor.ResumenDatosAppDTO
import tallerwapo.core.utils.RemotePCController


object GestionServidorServicios {

    fun apagarServidor() {
        CoroutineScope(Dispatchers.IO).launch {
            val api = AppContexto.gestionServidorApi
            api.apagarServidor()
        }
    }

    fun arrancarServidor() {
        val mackAddres = "sdfgdsgg"

        CoroutineScope(Dispatchers.IO).launch {
            RemotePCController.wakeOnLan(mackAddres)
        }
    }


    suspend fun getEstadoServidor(): ResumenDatosAppDTO? {
        val api = AppContexto.gestionServidorApi
        var respesta = api.getEstadoServidor()
        return respesta.BoRespuesta
    }

}