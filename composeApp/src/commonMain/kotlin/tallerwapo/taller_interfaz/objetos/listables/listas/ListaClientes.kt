package tallerwapo.taller_interfaz.objetos.listables.listas

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.listables.ListableBOList
import tallerwapo.taller_interfaz.objetos.listables.items.ClientesListItem
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@Composable
fun ListaClientes(
    clientes: List<ClienteBO>,
    clienteSeleccionado: ClienteBO?,
    onClienteSeleccionado: (ClienteBO) -> Unit,
    onClienteDoubleClick: ((ClienteBO) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(theme.paddingM)
        ) {
            AppTextos(text = "Clientes", style = theme.title)

            ListableBOList(
                items = clientes.map { ClientesListItem(it) },
                selectedItemId = clienteSeleccionado?.uuid,
                onItemClick = onClienteSeleccionado,
                onItemDoubleClick = onClienteDoubleClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
