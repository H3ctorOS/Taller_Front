package tallerwapo.core.apirest.interfaces

import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.dominio.dto.contabilidad.IngresoConCitaDTO
import tallerwapo.core.dominio.dto.contabilidad.IngresoDTO

interface IngresosApi {

    val CREAR_NUEVO: String get() = "/ingresos/crearNuevoIngreso"
    val CREAR_NUEVO_CITA: String get() = "/ingresos/crearNuevoIngresoCita"
    val BUSCAR_TODOS: String get() = "/ingresos/buscarTodos"
    val BUSCAR_POR_CITA: String get() = "/ingresos/buscarPorCita"
    val ACTUALIZAR: String get() = "/ingresos/actualizarIngreso"
    val ELIMINAR: String get() = "/ingresos/eliminarIngreso"

    /** Nuevo ingreso simple usando DTO */
    suspend fun guardarNuevoIngreso(ingreso: IngresoDTO): RespuestaDTO<IngresoDTO>

    /** Nuevo ingreso asociado a una cita usando DTO */
    suspend fun guardarNuevoIngreso(ingresoConCita: IngresoConCitaDTO): RespuestaDTO<IngresoConCitaDTO>

    suspend fun buscarTodos(): RespuestaDTO<List<IngresoDTO>>

    suspend fun buscarPorCita(citaUuid: String): RespuestaDTO<List<IngresoDTO>>

    suspend fun actualizarIngreso(ingreso: IngresoDTO): RespuestaDTO<IngresoDTO>

    suspend fun eliminarIngreso(ingreso: IngresoDTO): RespuestaDTO<IngresoDTO>
}
