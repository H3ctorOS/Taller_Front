package tallerwapo.taller_interfaz.objetos.listables.items

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Clock
import tallerwapo.core.contexto.AppContexto
import tallerwapo.core.dominio.bo.GastoBO
import tallerwapo.core.dominio.bo.IngresoBO
import tallerwapo.core.servicios.formatoDiaMesAnio
import tallerwapo.taller_interfaz.boDeInterfaz.CitaBoUI
import tallerwapo.taller_interfaz.objetos.botones.MasBoton
import tallerwapo.taller_interfaz.objetos.listables.interfaz.ListableBO
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.InterfazContext

data class CitasListItem(
    override val bo: CitaBoUI,
    val onNuevoIngresoClick: (CitaBoUI) -> Unit,
    val onNuevoGastoClick: (CitaBoUI) -> Unit
) : ListableBO<CitaBoUI> {

    override val titulo: String = bo.cita.concepto
    override val subtitulo: String =
        "${bo.cita.fechaInicio.formatoDiaMesAnio()} → ${bo.cita.fechaFinalizada.formatoDiaMesAnio()}"
    override val descripcion: String? = bo.cita.observaciones

    @Composable
    override fun ContenidoDesplegable() {
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
        val scope = rememberCoroutineScope()

        // --- Estados Ingreso ---
        var conceptoIngreso by remember { mutableStateOf("") }
        var importeIngreso by remember { mutableStateOf("") }

        // --- Estados Gasto ---
        var descripcionGasto by remember { mutableStateOf("") }
        var importeGasto by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(theme.paddingS)
        ) {
            // Información básica
            Text(text = "Observaciones: ${bo.cita.observaciones.ifEmpty { "Ninguna" }}")
            Text(text = "Estado: ${bo.cita.codigoEstado.ifEmpty { "Desconocido" }}")
            Text(text = "Inicio: ${bo.cita.fechaInicio.formatoDiaMesAnio()}")
            Text(text = "Fin: ${bo.cita.fechaFinalizada.formatoDiaMesAnio()}")

            Spacer(modifier = Modifier.height(16.dp))

            // --- Ingresos ---
            Text(text = "Ingresos", style = theme.subTitleText)
            bo.ingresos.forEach { ingreso ->
                Text("${ingreso.concepto} - ${ingreso.importe}€ - ${ingreso.fecha.formatoDiaMesAnio()}", style = theme.bodyText)
            }

            Spacer(modifier = Modifier.height(8.dp))
            MasBoton(
                onClick = {
                    val cantidad = importeIngreso.toDoubleOrNull()
                    if (conceptoIngreso.isNotBlank() && cantidad != null) {
                        // Crear ingreso directo
                        scope.launch(Dispatchers.IO) {
                            val ingreso = IngresoBO(
                                concepto = conceptoIngreso,
                                importe = cantidad,
                                fecha = Clock.System.now(),
                                codEstado = "ACTIVO",
                                observaciones = ""
                            )
                            AppContexto.ingresosRepo.crearIngreso(ingreso, bo.cita)
                        }
                        conceptoIngreso = ""
                        importeIngreso = ""
                    } else {
                        // Abrir formulario
                        onNuevoIngresoClick(bo)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Gastos ---
            Text(text = "Gastos", style = theme.subTitleText)
            bo.gastos.forEach { gasto ->
                Text("${gasto.descripcion} - ${gasto.importe}€ - ${gasto.fecha.formatoDiaMesAnio()}", style = theme.bodyText)
            }

            Spacer(modifier = Modifier.height(8.dp))
            MasBoton(
                onClick = {
                    val cantidad = importeGasto.toDoubleOrNull()
                    if (descripcionGasto.isNotBlank() && cantidad != null) {
                        // Crear gasto directo
                        scope.launch(Dispatchers.IO) {
                            val gasto = GastoBO(
                                descripcion = descripcionGasto,
                                importe = cantidad,
                                fecha = Clock.System.now(),
                                observaciones = ""
                            )
                            AppContexto.gastosRepo.crearGasto(gasto, bo.cita)
                        }
                        descripcionGasto = ""
                        importeGasto = ""
                    } else {
                        // Abrir formulario
                        onNuevoGastoClick(bo)
                    }
                }
            )
        }
    }
}
