package tallerwapo.taller_interfaz.objetos.listables.items

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Clock
import tallerwapo.core.contexto.AppContexto
import tallerwapo.core.dominio.bo.GastoBO
import tallerwapo.core.dominio.bo.IngresoBO
import tallerwapo.core.servicios.formatoDiaMesAnio
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.boDeInterfaz.CitaBoUI
import tallerwapo.taller_interfaz.objetos.botones.MasBoton
import tallerwapo.taller_interfaz.objetos.listables.interfaz.ListableBO
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.themes.interfaces.AppTheme

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
        val theme: AppTheme = AppThemeProvider.getTheme(InterfazContext.themeMode)
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
            Text(
                text = "Observaciones: ${bo.cita.observaciones.ifEmpty { "Ninguna" }}",
                style = theme.bodyText
            )
            Text(
                text = "Estado: ${bo.cita.codigoEstado.ifEmpty { "Desconocido" }}",
                style = theme.bodyText
            )
            Text(
                text = "Inicio: ${bo.cita.fechaInicio.formatoDiaMesAnio()}",
                style = theme.bodyText
            )
            Text(
                text = "Fin: ${bo.cita.fechaFinalizada.formatoDiaMesAnio()}",
                style = theme.bodyText
            )

            Spacer(modifier = Modifier.height(16.dp))

            // -------- INGRESOS --------
            Text(
                text = "Ingresos",
                style = theme.subTitleText,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            bo.ingresos.forEach { ingreso ->
                FilaMovimiento(
                    fecha = ingreso.fecha.formatoDiaMesAnio(),
                    concepto = ingreso.concepto,
                    importe = ingreso.importe,
                    theme = theme
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            MasBoton(
                onClick = {
                    val cantidad = importeIngreso.toDoubleOrNull()
                    if (conceptoIngreso.isNotBlank() && cantidad != null) {
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
                        onNuevoIngresoClick(bo)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // -------- GASTOS --------
            Text(
                text = "Gastos",
                style = theme.subTitleText,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            bo.gastos.forEach { gasto ->
                FilaMovimiento(
                    fecha = gasto.fecha.formatoDiaMesAnio(),
                    concepto = gasto.descripcion,
                    importe = gasto.importe,
                    theme = theme
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            MasBoton(
                onClick = {
                    val cantidad = importeGasto.toDoubleOrNull()
                    if (descripcionGasto.isNotBlank() && cantidad != null) {
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
                        onNuevoGastoClick(bo)
                    }
                }
            )
        }
    }

    // ---------- FILA TIPO TABLA (SIN CABECERA) ----------
    @Composable
    private fun FilaMovimiento(
        fecha: String,
        concepto: String,
        importe: Double,
        theme: AppTheme
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = theme.paddingS, vertical = 2.dp)
        ) {
            Text(
                text = fecha,
                modifier = Modifier.weight(1.2f),
                style = theme.bodyText
            )

            Text(
                text = concepto,
                modifier = Modifier.weight(2.5f),
                style = theme.bodyText,
                maxLines = 1
            )

            Text(
                text = "${"%.2f".format(importe)} €",
                modifier = Modifier.weight(1f),
                style = theme.bodyText,
                textAlign = TextAlign.End
            )
        }
    }
}
