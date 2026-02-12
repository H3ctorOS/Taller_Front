package tallerwapo.taller_interfaz.pantallas.calendario.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tallerwapo.core.dominio.dto.calendario.SemanasDelAnioDTO
import tallerwapo.core.servicios.CalendarioService
import tallerwapo.taller_interfaz.objetos.scroll.ScrollableContent
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.InterfazContext

/**
 * Panel lateral de meses y semanas con scroll vertical
 *
 * @param semanaActual Número de la semana que se está mostrando actualmente (1..52/53)
 * @param onSemanaSeleccionada Callback al seleccionar otra semana
 */
@Composable
fun PanelMesesSemanas(
    onSemanaSeleccionada: (Int) -> Unit = {}
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val mesesNombres = listOf(
        "Enero","Febrero","Marzo","Abril",
        "Mayo","Junio","Julio","Agosto",
        "Septiembre","Octubre","Noviembre","Diciembre"
    )

    var semanasDelAnioDTO by remember { mutableStateOf<SemanasDelAnioDTO?>(null) }
    var semanaSeleccionada by remember { mutableStateOf<Int?>(null) } // Estado interno de la semana seleccionada

    LaunchedEffect(Unit) {
        semanasDelAnioDTO = CalendarioService.getSemanasAnioActual()

        // ─── Seleccionar automáticamente la semana actual al cargar ───
        semanasDelAnioDTO?.semanasPorMes
            ?.values
            ?.flatten()
            ?.firstOrNull { it.esActual }
            ?.let { semanaActual ->
                semanaSeleccionada = semanaActual.numeroSemana
                onSemanaSeleccionada(semanaActual.numeroSemana)
            }
    }

    Box(
        modifier = Modifier
            .width(100.dp)
            .fillMaxHeight()
            .padding(4.dp)
    ) {
        ScrollableContent {
            semanasDelAnioDTO?.semanasPorMes?.toSortedMap()?.forEach { (mesNum, semanas) ->
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    val nombreMes = mesesNombres.getOrNull(mesNum - 1) ?: "Mes $mesNum"
                    AppTextos(
                        text = nombreMes,
                        style = theme.subTitleText,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.height(4.dp))

                    semanas.forEach { semana ->
                        val isSemanaActual = semana.esActual
                        val isSeleccionada = semana.numeroSemana == semanaSeleccionada

                        // ─── Elegir color según estado ───
                        val backgroundColor = when {
                            isSeleccionada -> theme.selectedBackgroundColor // Usuario seleccionó
                            isSemanaActual -> theme.selectedBackgroundColor.copy(alpha = 0.3f) // Semana real del servidor (suave)
                            else -> theme.surfaceColor
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                                .height(24.dp)
                                .background(
                                    color = backgroundColor,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable {
                                    semanaSeleccionada = semana.numeroSemana
                                    onSemanaSeleccionada(semana.numeroSemana)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            AppTextos(
                                text = semana.numeroSemana.toString(),
                                style = theme.bodyText,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}


