package tallerwapo.taller_interfaz.formularios.configuracion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import tallerwapo.core.servicios.ConfigServices
import tallerwapo.taller_interfaz.objetos.campoEntrada.CampoEntradaRow
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.campoEntrada.validaciones.ValidacionesCampoEntrada
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@Composable
fun FormularioConfiguracionApp(
    onCerrar: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    // --- Estados locales de IP y MAC ---
    var ipServidor by remember { mutableStateOf(ConfigServices.obtenerIp()) }
    var macServidor by remember { mutableStateOf(ConfigServices.obtenerMac()) }

    val validaciones = ValidacionesCampoEntrada()

    Column(modifier = Modifier) {

        // --- Campo IP ---
        CampoEntradaRow(
            titulo = "IP del Servidor",
            valor = ipServidor,
            onValueChange = { nuevaIp -> ipServidor = nuevaIp },
            modifier = Modifier,
            validaciones = listOf(validaciones.validarNoVacio,validaciones.validarIp)
        )

        Spacer(Modifier.height(theme.paddingM))

        // --- Campo MAC ---
        CampoEntradaRow(
            titulo = "MAC del Servidor",
            valor = macServidor,
            onValueChange = { nuevaMac -> macServidor = nuevaMac },
            modifier = Modifier,
            validaciones = listOf(validaciones.validarNoVacio,validaciones.validarMac)
        )

        Spacer(Modifier.height(theme.paddingM))

        // --- Botón Guardar ---
        AppBoton(
            text = "Guardar",
            onClick = {
                // Solo guardar si ambos campos son válidos
                val ipValida = validaciones.validarIp.funcion(ipServidor)
                val macValida = validaciones.validarMac.funcion(macServidor)

                if (ipValida && macValida) {
                    ConfigServices.actualizarIp(ipServidor)
                    ConfigServices.actualizarMac(macServidor)
                    onCerrar()
                }
            }
        )
    }
}