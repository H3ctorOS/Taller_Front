package tallerwapo.taller_interfaz.pantallas.principal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.pantallas.calendario.DiasSemanaScreen
import tallerwapo.taller_interfaz.pantallas.principal.componentesPC.AppSidebar
import tallerwapo.taller_interfaz.pantallas.principal.componentesPC.BarraInferiorPC
import tallerwapo.taller_interfaz.themes.AppThemeProvider


class PrincipalPCScreen : Screen {

    @Composable
    override fun Content() {
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
        // Estado de la pantalla derecha
        var currentScreen by remember { mutableStateOf<Screen>(DiasSemanaScreen()) }

        Box(Modifier.fillMaxSize().defaultMinSize(minHeight = 2000.dp, minWidth = 2000.dp)) {
            Column(modifier = Modifier.fillMaxSize().background(theme.backgroundColor)) {

                Row(modifier = Modifier.weight(1f).fillMaxWidth()) {

                    // Izquierda Sidebar fijo
                    AppSidebar { screen -> currentScreen = screen }

                    // Contenido derecho
                    Column(modifier = Modifier.fillMaxSize().background(theme.backgroundColor)) {
                        currentScreen.Content()
                    }
                }

                // Barra inferior
                BarraInferiorPC.Contenido()
            }
        }
    }


}