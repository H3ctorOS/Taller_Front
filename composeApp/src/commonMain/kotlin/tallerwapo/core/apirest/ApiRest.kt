package tallerwapo.core.apirest

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.utils.Logs

object ApiRest {
    val httpClient: HttpClient = HttpClientProvider.httpCliente

    // --------------------
    // POST genérico
    // --------------------
    suspend inline fun <reified T : Any> post(
        url: String,
        body: T
    ): RespuestaDTO<T> {
        Logs.info(this,"POST -> $url")

        val jsonString = Json.encodeToString(body)
        Logs.debug(this,"Request Body JSON: $jsonString")

        return try {
            val response = httpClient.request(url) {
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                setBody(body)
            }

            val result: RespuestaDTO<T> = response.body()

            Logs.debug(this,"Respuesta: $result")

            result

        } catch (e: Exception) {
            Logs.error(this,"Error en POST $url: ${e.message}")
            RespuestaDTO(
                status = 500,
                mensaje = e.message ?: "Error desconocido",
                isOk = false,
                objeto = null
            )
        }
    }

    // --------------------
    // GET genérico
    // --------------------
    suspend inline fun <reified T : Any> get(
        url: String
    ): RespuestaDTO<T> {
        Logs.info(this,"GET -> $url")

        return try {
            val response = httpClient.request(url) {
                method = HttpMethod.Get
            }

            val result: RespuestaDTO<T> = response.body()

            Logs.debug(this,"Respuesta: $result")

            result

        } catch (e: Exception) {
            Logs.error(this,"Error en GET $url: ${e.message}")
            RespuestaDTO(
                status = 500,
                mensaje = e.message ?: "Error desconocido",
                isOk = false,
                objeto = null
            )
        }
    }
}