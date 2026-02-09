package tallerwapo.core.servicios

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.AppContexto
import tallerwapo.core.utils.RemotePCController


object GestionSistemaServicios {

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

}