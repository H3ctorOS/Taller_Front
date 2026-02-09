package tallerwapo.core.apirest.implementaciones

import tallerwapo.core.apirest.ApiConfig.BASE_URL
import tallerwapo.core.apirest.ApiRest
import tallerwapo.core.apirest.interfaces.GestionServidorApi


class GestionServidorApiImpl : GestionServidorApi {

    override suspend fun apagarServidor() {
        ApiRest.post(
            url = BASE_URL + APAGAR,
            body = ""
        )
    }

}
