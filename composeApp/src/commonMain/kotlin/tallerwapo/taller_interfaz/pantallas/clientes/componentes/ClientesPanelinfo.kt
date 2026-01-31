package tallerwapo.taller_interfaz.pantallas.clientes.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@Composable
fun ClientesPanelinfo(cliente: ClienteBO?, modifier: Modifier = Modifier) {

    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)


    Box(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            if (cliente == null) {
                AppTextos(text = "Selecciona un cliente", style = theme.bodyText)

            } else {
                AppTextos(text = cliente.nombre, style = theme.bodyText, modifier = Modifier.weight(1f))
                AppTextos(text = cliente.apellidos, style = theme.bodyText, modifier = Modifier.weight(1f))
                AppTextos(text = cliente.apellidos, style = theme.bodyText, modifier = Modifier.weight(1f))
                AppTextos(text = cliente.apellidos, style = theme.bodyText, modifier = Modifier.weight(1f))
                AppTextos(text = cliente.apellidos, style = theme.bodyText, modifier = Modifier.weight(1f))
                AppTextos(text = cliente.apellidos, style = theme.bodyText, modifier = Modifier.weight(1f))
                AppTextos(text = "puta mierda ", style = theme.bodyText, modifier = Modifier.weight(1f))
            }
        }
    }
}