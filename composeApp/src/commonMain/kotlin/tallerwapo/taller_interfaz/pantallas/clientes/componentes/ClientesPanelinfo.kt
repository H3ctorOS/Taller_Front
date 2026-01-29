package tallerwapo.taller_interfaz.pantallas.clientes.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppTheme

@Composable
fun ClientesPanelinfo(cliente: ClienteBO?, modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            if (cliente == null) {
                AppTextos(text = "Selecciona un cliente", style = AppTheme.Body)

            } else {
                AppTextos(text = cliente.nombre, style = AppTheme.Body, modifier = Modifier.weight(1f))
                AppTextos(text = cliente.apellidos, style = AppTheme.Body, modifier = Modifier.weight(1f))
                AppTextos(text = cliente.apellidos, style = AppTheme.Body, modifier = Modifier.weight(1f))
                AppTextos(text = cliente.apellidos, style = AppTheme.Body, modifier = Modifier.weight(1f))
                AppTextos(text = cliente.apellidos, style = AppTheme.Body, modifier = Modifier.weight(1f))
                AppTextos(text = cliente.apellidos, style = AppTheme.Body, modifier = Modifier.weight(1f))
                AppTextos(text = "puta mierda ", style = AppTheme.Body, modifier = Modifier.weight(1f))
            }
        }
    }
}