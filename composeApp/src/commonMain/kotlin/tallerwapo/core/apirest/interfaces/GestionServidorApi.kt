package tallerwapo.core.apirest.interfaces



interface GestionServidorApi {

    val APAGAR: String get() = "/gestion/sistema/apagarEquipo"

    suspend fun apagarServidor()
}