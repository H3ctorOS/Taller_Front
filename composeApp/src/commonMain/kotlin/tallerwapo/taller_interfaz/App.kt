package tallerwapo.taller_interfaz

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import tallerwapo.taller_interfaz.objetos.emergentes.MensajesEmergentes
import tallerwapo.taller_interfaz.pantallas.principal.PrincipalPCScreen
import tallerwapo.core.utils.DeviceType
import tallerwapo.core.utils.getDeviceType
import tallerwapo.taller_interfaz.pantallas.principal.PrincipalScreenMobile

@Composable
@Preview
fun App() {

    val deviceType = getDeviceType()

    MaterialTheme {

        when (deviceType) {
            DeviceType.MOBILE -> AppMovil()
            DeviceType.TABLET,
            DeviceType.DESKTOP -> AppPC()
        }

        // Emergentes sobre la pantalla
        MensajesEmergentes.Contenido()
    }
}

@Composable
private fun AppPC() {
    Navigator(screen = PrincipalPCScreen()) { navigator ->
        SlideTransition(navigator)
    }
}

@Composable
private fun AppMovil() {
    Navigator(screen = PrincipalScreenMobile) { navigator ->
        SlideTransition(navigator)
    }
}
