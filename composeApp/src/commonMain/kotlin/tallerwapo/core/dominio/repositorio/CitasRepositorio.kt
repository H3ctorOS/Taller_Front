package tallerwapo.core.dominio.repositorio

import tallerwapo.core.apirest.interfaces.CitasApi
import tallerwapo.core.dominio.bo.CitaBO
import tallerwapo.core.dominio.bo.VehiculoBO
import tallerwapo.core.dominio.dto.CitaDTO
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.dto.VehiculoDTO
import tallerwapo.core.dominio.dto.calendario.CitaSemanaDTO
import tallerwapo.core.utils.Logs


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

    suspend fun citasSemanaActual(): CitaSemanaDTO {
        val ahora = System.currentTimeMillis()
        val unaHora = 60 * 60 * 1000L // 1 hora en ms

        // Suponemos que la semana actual empieza hoy (lunes)
        val inicioSemana = ahora
        val finSemana = inicioSemana + 4 * 24 * 60 * 60 * 1000L // lunes a viernes

        return CitaSemanaDTO(
            numeroSemana = 1,
            fechaLunes = inicioSemana,
            fechaMartes = inicioSemana + 1 * 24 * 60 * 60 * 1000L,
            fechaMiercoles = inicioSemana + 2 * 24 * 60 * 60 * 1000L,
            fechaJueves = inicioSemana + 3 * 24 * 60 * 60 * 1000L,
            fechaViernes = inicioSemana + 4 * 24 * 60 * 60 * 1000L,
            citasLunes = listOf(
                crearCitaSimulada(1, "Cita Lunes 1", 0),
                crearCitaSimulada(2, "Cita Lunes 2", 0, 11)
            ),
            citasMartes = listOf(
                crearCitaSimulada(3, "Cita Martes 1", 1),
                crearCitaSimulada(4, "Cita Martes 2", 1, 11)
            ),
            citasMiercoles = listOf(
                crearCitaSimulada(5, "Cita Miércoles 1", 2),
                crearCitaSimulada(6, "Cita Miércoles 2", 2, 11)
            ),
            citasJueves = listOf(
                crearCitaSimulada(7, "Cita Jueves 1", 3),
                crearCitaSimulada(8, "Cita Jueves 2", 3, 11)
            ),
            citasViernes = listOf(
                crearCitaSimulada(9, "Cita Viernes 1", 4),
                crearCitaSimulada(10, "Cita Viernes 2", 4, 11)
            )
        )
    }

    suspend fun citasSemana(numeroSemana: Int): CitaSemanaDTO {
        val ahora = System.currentTimeMillis()
        val unaHora = 60 * 60 * 1000L

        // Cada semana empieza numeroSemana-1 semanas después de "ahora"
        val inicioSemana = ahora + (numeroSemana - 1) * 7 * 24 * 60 * 60 * 1000L
        val finSemana = inicioSemana + 4 * 24 * 60 * 60 * 1000L

        return CitaSemanaDTO(
            numeroSemana = numeroSemana,
            fechaLunes = inicioSemana,
            fechaMartes = inicioSemana + 1 * 24 * 60 * 60 * 1000L,
            fechaMiercoles = inicioSemana + 2 * 24 * 60 * 60 * 1000L,
            fechaJueves = inicioSemana + 3 * 24 * 60 * 60 * 1000L,
            fechaViernes = inicioSemana + 4 * 24 * 60 * 60 * 1000L,
            citasLunes = listOf(
                crearCitaSimulada(100 + numeroSemana * 10 + 1, "Cita Lunes", 0),
                crearCitaSimulada(100 + numeroSemana * 10 + 2, "Cita Lunes Extra", 0, 11)
            ),
            citasMartes = listOf(
                crearCitaSimulada(200 + numeroSemana * 10 + 1, "Cita Martes", 1),
                crearCitaSimulada(200 + numeroSemana * 10 + 2, "Cita Martes Extra", 1, 11)
            ),
            citasMiercoles = listOf(
                crearCitaSimulada(300 + numeroSemana * 10 + 1, "Cita Miércoles", 2),
                crearCitaSimulada(300 + numeroSemana * 10 + 2, "Cita Miércoles Extra", 2, 11)
            ),
            citasJueves = listOf(
                crearCitaSimulada(400 + numeroSemana * 10 + 1, "Cita Jueves", 3),
                crearCitaSimulada(400 + numeroSemana * 10 + 2, "Cita Jueves Extra", 3, 11)
            ),
            citasViernes = listOf(
                crearCitaSimulada(500 + numeroSemana * 10 + 1, "Cita Viernes", 4),
                crearCitaSimulada(500 + numeroSemana * 10 + 2, "Cita Viernes Extra", 4, 11)
            )
        )
    }




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
