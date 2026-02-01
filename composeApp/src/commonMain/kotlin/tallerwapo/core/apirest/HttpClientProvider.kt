package tallerwapo.core.apirest

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


object HttpClientProvider {

    val httpCliente = HttpClient {
        install(ContentNegotiation){
                json(
                    json = Json{
                        ignoreUnknownKeys = true
                        encodeDefaults = true  // esto fuerza a serializar valores por defecto, incluyendo null
                        explicitNulls = false    // esto fuerza a serializar nulls
                    },
                    contentType = ContentType.Any
                )
        }
    }
}