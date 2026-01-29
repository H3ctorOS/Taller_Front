package tallerwapo.taller_interfaz.pantallas.clientes

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.taller_interfaz.pantallas.clientes.componentes.ClientesPanelinfo
import tallerwapo.taller_interfaz.pantallas.clientes.componentes.ListaClientes
import tallerwapo.taller_interfaz.themes.AppTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tallerwapo.taller_interfaz.objetos.textos.AppTextos

class ClientesScreen : Screen {

    @Composable
    override fun Content() {

        var clienteSeleccionado by remember { mutableStateOf<ClienteBO?>(null) }

        Row(modifier = Modifier.fillMaxSize()) {

            // ListaClientes a la izquierda
            ListaClientes(onClienteSeleccionado = { cliente -> clienteSeleccionado = cliente },
                modifier = Modifier.width(300.dp).fillMaxHeight())

            // Columna derecha
            Column(modifier = Modifier.fillMaxHeight().weight(1f).padding(AppTheme.PaddingM)) {

                //Lista de coches del cliente
                Box(modifier = Modifier.width(300.dp)) {
                    AppTextos(text = "Coches", style = AppTheme.Title)
                }

                Spacer(modifier = Modifier.weight(1f))

                ClientesPanelinfo(clienteSeleccionado ,
                    modifier = Modifier.height(80.dp).fillMaxWidth())



            }



        }


    }
}
