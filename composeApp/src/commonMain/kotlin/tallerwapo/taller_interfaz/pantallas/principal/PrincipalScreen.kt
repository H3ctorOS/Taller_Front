package tallerwapo.taller_interfaz.pantallas.principal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import tallerwapo.taller_interfaz.pantallas.principal.componentes.AppSidebar
import tallerwapo.taller_interfaz.pantallas.principal.componentes.BarraInferior
import tallerwapo.taller_interfaz.pantallas.pruebas.PruebasScreen
import tallerwapo.taller_interfaz.themes.AppTheme


class PrincipalScreen : Screen {

    @Composable
    override fun Content() {

        // Estado de la pantalla derecha
        var currentScreen by remember { mutableStateOf<Screen>(PruebasScreen()) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.Background)
        ) {

            // Contenido principal con sidebar y contenido derecho
            Row(
                modifier = Modifier
                    .weight(1f) // ocupa todo el espacio restante dejando espacio para la barra inferior
                    .fillMaxWidth()
            ) {

                // Sidebar fijo
                AppSidebar { screen -> currentScreen = screen }

                // Contenido derecho con scroll
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {

                    // Contenedor para el contenido principal
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(AppTheme.Surface, AppTheme.CornerRadius)
                    ) {
                        currentScreen.Content()
                    }
                }
            }

            // Barra inferior
            BarraInferior.Contenido()
        }
    }
}