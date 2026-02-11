package tallerwapo.taller_interfaz.objetos.listables.items

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Clock
import tallerwapo.core.contexto.AppContexto
import tallerwapo.core.dominio.bo.GastoBO
import tallerwapo.core.dominio.bo.IngresoBO
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.boDeInterfaz.CitaBoUI
import tallerwapo.taller_interfaz.objetos.botones.MasBoton
import tallerwapo.taller_interfaz.objetos.listables.interfaz.ListableBO
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.themes.interfaces.AppTheme
import androidx.compose.ui.Alignment
import tallerwapo.core.servicios.CalendarioService

data class CitasListItem(
    override val bo: CitaBoUI,
    val onNuevoIngresoClick: (CitaBoUI) -> Unit,
    val onNuevoGastoClick: (CitaBoUI) -> Unit
) : ListableBO<CitaBoUI> {

    override val titulo: String = bo.cita.concepto
    override val subtitulo: String =
        "${CalendarioService.formatoDiaMesAnio(bo.cita.fechaInicio)}    →    ${
            CalendarioService.formatoDiaMesAnio(bo.cita.fechaFinalizada)
        }"
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

        // --- Contabilidad ---
        val totalIngresado = bo.ingresos.sumOf { it.importe }
        val totalGastado = bo.gastos.sumOf { it.importe }
        val ganado = totalIngresado - totalGastado

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(theme.paddingS)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            // -------- CONTABILIDAD --------
            Box(modifier = Modifier.width(300.dp).align(Alignment.CenterHorizontally)) {
                Column() {
                    AppTextos(
                        text = "Contabilidad",
                        style = theme.subTitleText,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    FilaContable("Ingresado", totalIngresado, theme)
                    FilaContable("Gastado", totalGastado, theme)
                    FilaContable("Ganado", ganado, theme)
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // -------- INGRESOS --------
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Spacer flexible a la izquierda
                Spacer(modifier = Modifier.weight(1f))

                // Título centrado
                AppTextos(
                    text = "Ingresos",
                    style = theme.subTitleText,
                    modifier = Modifier.wrapContentWidth()
                )

                // Spacer flexible a la derecha para centrar
                Spacer(modifier = Modifier.weight(1f))

                // Botón +
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
            }


            bo.ingresos.forEach { ingreso ->
                FilaMovimiento(
                    fecha =  CalendarioService.formatoDiaMesAnio(ingreso.fecha),
                    concepto = ingreso.concepto,
                    importe = ingreso.importe,
                    theme = theme
                )
            }

            Spacer(modifier = Modifier.height(8.dp))



            Spacer(modifier = Modifier.height(16.dp))

            // -------- GASTOS --------
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))

                AppTextos(
                    text = "Gastos",
                    style = theme.subTitleText,
                    modifier = Modifier.wrapContentWidth()
                )

                Spacer(modifier = Modifier.weight(1f))

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


            bo.gastos.forEach { gasto ->
                FilaMovimiento(
                    fecha =  CalendarioService.formatoDiaMesAnio(gasto.fecha),
                    concepto = gasto.descripcion,
                    importe = gasto.importe,
                    theme = theme
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


        }
    }

    // ---------- FILA CONTABILIDAD ----------
    @Composable
    private fun FilaContable(
        label: String,
        valor: Double,
        theme: AppTheme
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = theme.paddingS, vertical = 2.dp)
        ) {
            AppTextos(
                text = "$label:",
                style = theme.bodyText,
                modifier = Modifier.weight(1f)
            )
            AppTextos(
                text = "${"%.2f".format(valor)} €",
                style = theme.bodyText,
                modifier = Modifier.weight(1f)
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
            AppTextos(
                text = fecha,
                style = theme.bodyText,
                modifier = Modifier.weight(1.2f)
            )

            AppTextos(
                text = concepto,
                style = theme.bodyText,
                modifier = Modifier.weight(2.5f)
            )

            AppTextos(
                text = "${"%.2f".format(importe)} €",
                style = theme.bodyText,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
