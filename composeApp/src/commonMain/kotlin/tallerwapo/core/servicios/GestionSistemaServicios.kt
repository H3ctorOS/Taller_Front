package tallerwapo.core.servicios

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.ApiContexto


object GestionSistemaServicios {

    fun apagarServidor() {
        CoroutineScope(Dispatchers.IO).launch {
            val api = ApiContexto.gestionServidorApi
            api.apagarServidor()
        }
    }

}