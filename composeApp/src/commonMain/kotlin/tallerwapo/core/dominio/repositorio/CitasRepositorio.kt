package tallerwapo.core.dominio.repositorio

import tallerwapo.core.apirest.interfaces.CitasApi
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.dominio.dto.CitaDTO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.dto.VehiculoDTO
import tallerwapo.core.dominio.dto.calendario.CitaSemanaDTO
import tallerwapo.core.dominio.dto.calendario.DiaSemana
import tallerwapo.core.utils.Logs
import kotlin.random.Random


class CitasRepositorio(
    private val apiRest: CitasApi
) {

    // Crear cita: recibe BO, convierte a DTO para la API, devuelve BO
    suspend fun crearCita(cita: CitaBO): RespuestaDTO<CitaBO> {
        Logs.info(this, "Creando nueva cita: $cita")

        // BO -> DTO usando constructor del DTO
        val dto = CitaDTO(cita)

        val respuestaDTO: RespuestaDTO<CitaDTO> = apiRest.crearCita(dto)

        // DTO -> BO usando constructor de CitaBO
        val boRespuesta = respuestaDTO.BoRespuesta?.let { CitaBO(it) }

        val respuestaBO = RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boRespuesta,
            isOk = respuestaDTO.isOk
        )

        Logs.info(this, "Respuesta al crear la cita: ${respuestaBO.mensaje}")
        return respuestaBO
    }

    // Buscar todas las citas
    suspend fun buscarTodas(): RespuestaDTO<List<CitaBO>> {
        Logs.info(this, "Buscando todas las citas")
        val respuestaDTO: RespuestaDTO<List<CitaDTO>> = apiRest.buscarTodas()

        // Convertir lista de DTOs -> lista de BOs
        val boList = respuestaDTO.BoRespuesta?.map { CitaBO(it) } ?: emptyList()

        val respuesta = RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boList,
            isOk = respuestaDTO.isOk
        )

        Logs.info(this, "Cantidad de citas recibidas: ${boList.size}")
        return respuesta
    }

    // Buscar citas por vehículo
    suspend fun buscarPorVehiculo(vehiculoBO: VehiculoBO): RespuestaDTO<List<CitaBO>> {
        Logs.info(this, "Buscando citas del vehiculo: $vehiculoBO")

        // BO -> DTO usando constructor del DTO
        val vehiculoDTO = VehiculoDTO(vehiculoBO)

        val respuestaDTO: RespuestaDTO<List<CitaDTO>> = apiRest.buscarPorVehiculo(vehiculoDTO)

        // Convertir lista de DTOs -> lista de BOs
        val boList = respuestaDTO.BoRespuesta?.map { CitaBO(it) } ?: emptyList()

        val respuestaBO = RespuestaDTO(
            status = respuestaDTO.status,
            mensaje = respuestaDTO.mensaje,
            BoRespuesta = boList,
            isOk = respuestaDTO.isOk
        )

        Logs.info(this, "Cantidad de citas recibidas: ${boList.size}")
        return respuestaBO
    }

    // ───────── Semana actual ─────────
    suspend fun citasSemanaActual(): CitaSemanaDTO {
        val ahora = System.currentTimeMillis()

        // Suponemos que la semana actual empieza hoy (lunes)
        val dias = listOf(DiaSemana.LUNES, DiaSemana.MARTES, DiaSemana.MIERCOLES, DiaSemana.JUEVES, DiaSemana.VIERNES)

        val fechasMap = mutableMapOf<DiaSemana, Long>()
        val citasMap = mutableMapOf<DiaSemana, List<CitaDTO>>()

        dias.forEachIndexed { index, dia ->
            val fechaDia = ahora + index * 24 * 60 * 60 * 1000L
            fechasMap[dia] = fechaDia
            citasMap[dia] = listOf(crearCitaSimulada(Random.nextInt(), "Cita de prueba", index.toLong()))
        }

        return CitaSemanaDTO(
            numeroSemana = 1,
            fechas = fechasMap,
            citas = citasMap
        )
    }

    // ───────── Semana específica ─────────
    suspend fun citasSemana(numeroSemana: Int): CitaSemanaDTO {
        val ahora = System.currentTimeMillis()
        val dias = listOf(DiaSemana.LUNES, DiaSemana.MARTES, DiaSemana.MIERCOLES, DiaSemana.JUEVES, DiaSemana.VIERNES)

        // Cada semana empieza numeroSemana-1 semanas después de "ahora"
        val inicioSemana = ahora + (numeroSemana - 1) * 7 * 24 * 60 * 60 * 1000L

        val fechasMap = mutableMapOf<DiaSemana, Long>()
        val citasMap = mutableMapOf<DiaSemana, List<CitaDTO>>()

        dias.forEachIndexed { index, dia ->
            val fechaDia = inicioSemana + index * 24 * 60 * 60 * 1000L
            fechasMap[dia] = fechaDia
            citasMap[dia] = listOf(crearCitaSimulada(Random.nextInt(), "Cita semana $numeroSemana", index.toLong()))
        }

        return CitaSemanaDTO(
            numeroSemana = numeroSemana,
            fechas = fechasMap,
            citas = citasMap
        )
    }

    // ───────── Crear cita simulada ─────────
    fun crearCitaSimulada(id: Int, concepto: String, diasOffset: Long, horaInicio: Long = 9): CitaDTO {
        val ahora = System.currentTimeMillis()
        val unaHora = 60 * 60 * 1000L // 1 hora en ms

        val inicio = ahora + diasOffset * 24 * 60 * 60 * 1000L + horaInicio * unaHora
        val fin = inicio + unaHora
        return CitaDTO(
            uuid = id,
            vehiculoUuid = 1,
            concepto = concepto,
            fechaInicio = inicio,
            fechaFinalizada = fin,
            codigoEstado = "ACTIVO",
            observaciones = "Observación de prueba"
        )
    }

}
