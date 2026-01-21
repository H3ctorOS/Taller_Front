package tallerwapo.taller_interfaz

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.tallerwapo.interfazgrafica.taller_interfazgrafica.pantallas.principal.Principal_Screen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(screen = Principal_Screen()){ navigator ->
            SlideTransition(navigator)
        }
    }
}

@Composable
@Preview
fun AppPC() {
    MaterialTheme {
        Navigator(screen = Principal_Screen()){navigator ->
            SlideTransition(navigator)
        }
    }
}