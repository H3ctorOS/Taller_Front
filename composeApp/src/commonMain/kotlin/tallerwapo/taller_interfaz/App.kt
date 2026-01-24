package tallerwapo.taller_interfaz

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import tallerwapo.taller_interfaz.emergentes.MensajesEmergentes
import tallerwapo.taller_interfaz.pantallas.principal.PrincipalScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(screen = PrincipalScreen()){ navigator ->
            SlideTransition(navigator)
        }

        // Emergentes sobre la pantalla
        MensajesEmergentes.Contenido()
    }
}

