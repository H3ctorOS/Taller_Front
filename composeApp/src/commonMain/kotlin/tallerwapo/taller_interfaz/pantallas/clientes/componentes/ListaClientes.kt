package tallerwapo.taller_interfaz.pantallas.clientes.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tallerwapo.core.contexto.ApiContexto
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.taller_interfaz.objetos.emergentes.MensajesEmergentes
import tallerwapo.taller_interfaz.objetos.listables.ListableBOList
import tallerwapo.taller_interfaz.objetos.listables.items.ClientesListItem
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppTheme

@Composable
fun ListaClientes(
    onClienteSeleccionado: (ClienteBO) -> Unit,
    modifier: Modifier = Modifier
) {
    val clientesRepo = ApiContexto.clientesRepo
    var listaClientes by remember { mutableStateOf<List<ClienteBO>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            clientesRepo.buscarTodosLosClientes()?.let {
                listaClientes = it
            } ?: MensajesEmergentes.mostrarDialogo(
                titulo = "Error",
                mensaje = "No se han podido cargar los clientes",
                botones = listOf(
                    MensajesEmergentes.BotonDialogo("Ok") {}
                )
            )
        } catch (e: Exception) {
            MensajesEmergentes.mostrarDialogo(
                titulo = "Error",
                mensaje = e.message ?: "Error desconocido",
                botones = listOf(
                    MensajesEmergentes.BotonDialogo("Ok") {}
                )
            )
        }
    }

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.PaddingM)
        ) {
            AppTextos(text = "Clientes", style = AppTheme.Title)

            ListableBOList(
                items = listaClientes.map { ClientesListItem(it) },
                onItemClick = onClienteSeleccionado,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
