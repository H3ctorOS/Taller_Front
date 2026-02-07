package tallerwapo.core.apirest.interfaces

import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.dto.contabilidad.GastoConCitaDTO
import tallerwapo.core.dominio.dto.contabilidad.GastoDTO

interface GastosApi {

    val CREAR_NUEVO: String get() = "/gastos/crearNuevoGasto"
    val CREAR_NUEVO_CITA: String get() = "/gastos/crearNuevoGastoCita"
    val BUSCAR_TODOS: String get() = "/gastos/buscarTodos"
    val BUSCAR_POR_CITA: String get() = "/gastos/buscarPorCita"
    val ACTUALIZAR: String get() = "/gastos/actualizarGasto"
    val ELIMINAR: String get() = "/gastos/eliminarGasto"

    /** Nuevo gasto simple usando DTO */
    suspend fun guardarNuevoGasto(gasto: GastoDTO): RespuestaDTO<GastoDTO>

    /** Nuevo gasto asociado a una cita usando DTO */
    suspend fun guardarNuevoGasto(gastoConCita: GastoConCitaDTO): RespuestaDTO<GastoConCitaDTO>

    suspend fun buscarTodos(): RespuestaDTO<List<GastoDTO>>

    suspend fun buscarPorCita(citaUuid: String): RespuestaDTO<List<GastoDTO>>

    suspend fun actualizarGasto(gasto: GastoDTO): RespuestaDTO<GastoDTO>

    suspend fun eliminarGasto(gasto: GastoDTO): RespuestaDTO<GastoDTO>
}
