package tallerwapo.taller_interfaz.pantallas.calendario

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.AppContexto.citasRepo
import tallerwapo.core.dominio.dto.calendario.CitaSemanaDTO
import tallerwapo.core.dominio.dto.calendario.DiaSemana
import tallerwapo.taller_interfaz.boDeInterfaz.CitaBoUI
import tallerwapo.taller_interfaz.objetos.listables.listas.ListaCitas
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.formularios.citas.FormularioNuevaCita
import tallerwapo.taller_interfaz.objetos.emergentes.FormularioEmergente
import tallerwapo.taller_interfaz.pantallas.calendario.componentes.PanelMesesSemanas
import java.util.*

class DiasSemanaScreen : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

        var semanaDTO by remember { mutableStateOf<CitaSemanaDTO?>(null) }
        var mostrarFormularioNuevaCita by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // ─── Panel izquierdo con meses y semanas ───
            PanelMesesSemanas(
                onSemanaSeleccionada = { semanaSeleccionada ->
                    scope.launch {
                        semanaDTO = citasRepo.citasSemana(semanaSeleccionada)
                    }
                }
            )

            Spacer(Modifier.width(8.dp))

            // ─── Panel derecho con días de la semana ───
            semanaDTO?.let { s ->
                val dias = listOf(
                    DiaSemana.LUNES,
                    DiaSemana.MARTES,
                    DiaSemana.MIERCOLES,
                    DiaSemana.JUEVES,
                    DiaSemana.VIERNES
                )

                // Convertimos cada día a (NombreDia, fecha Long, List<CitaBoUI>)
                val diasConFecha = dias.mapNotNull { dia ->
                    s.fechas[dia]?.takeIf { it > 0 }?.let { fecha ->
                        Triple(
                            dia.name.replaceFirstChar { it.uppercase() }, // "LUNES" -> "Lunes"
                            fecha,
                            s.getCitasBO(dia).map { CitaBoUI(it) }
                        )
                    }
                }

                // Agrupar días por mes usando Calendar
                val diasAgrupadosPorMes = diasConFecha.groupBy { (_, fecha, _) ->
                    val cal = Calendar.getInstance()
                    cal.timeInMillis = fecha
                    cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                        .replaceFirstChar { it.uppercase() }
                }

                val diasOrdenados = diasAgrupadosPorMes.flatMap { it.value }

                Column(modifier = Modifier.fillMaxWidth()) {
                    // ─── Fila de nombres de meses ───
                    Row(modifier = Modifier.fillMaxWidth()) {
                        diasAgrupadosPorMes.forEach { (mes, diasMes) ->
                            Row(modifier = Modifier.weight(diasMes.size.toFloat())) {
                                AppTextos(
                                    text = mes,
                                    style = theme.subTitleText,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    // ─── Fila de días y citas ───
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        diasOrdenados.forEach { (nombreDia, fecha, citasUI) ->
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            ) {
                                val cal = Calendar.getInstance()
                                cal.timeInMillis = fecha
                                val diaNumero = cal.get(Calendar.DAY_OF_MONTH)

                                AppTextos(
                                    text = "$nombreDia $diaNumero",
                                    style = theme.title,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )

                                Spacer(Modifier.height(4.dp))

                                ListaCitas(
                                    listaCitasUI = citasUI,
                                    citaSeleccionada = null,
                                    onCitaSeleccionada = {},
                                    onCitaDoubleClick = null,
                                    onNewClick = { mostrarFormularioNuevaCita = true },
                                    onNuevoIngresoClick = {},
                                    onNuevoGastoClick = {},
                                    mostrarNew = true,
                                    modifier = Modifier.fillMaxHeight()
                                )
                            }
                        }
                    }
                }
            }
        }

        // ─── Formulario nueva cita ───
        FormularioEmergente(
            mostrar = mostrarFormularioNuevaCita,
            onCerrar = { mostrarFormularioNuevaCita = false }
        ) {
            FormularioNuevaCita(onCerrar = { mostrarFormularioNuevaCita = false })
        }
    }
}
