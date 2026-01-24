package tallerwapo.taller_interfaz.pantallas.formularios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.ApiContexto
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.utils.Logs
import tallerwapo.taller_interfaz.pantallas.formularios.objetos.CampoEntradaRow
import tallerwapo.taller_interfaz.themes.AppTheme
@Composable
fun FormularioNuevoCliente(
    onCerrar: () -> Unit
) {
    val clientesApi = ApiContexto.clientesApi

    // Estados de los campos
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .widthIn(max = 900.dp)
            .background(AppTheme.Surface, AppTheme.CornerRadius)
            .padding(AppTheme.PaddingM)
    ) {
        Text(
            text = "Nuevo Cliente",
            style = AppTheme.Title,
            modifier = Modifier.padding(bottom = AppTheme.PaddingL)
        )

        Spacer(Modifier.height(AppTheme.PaddingL))

        CampoEntradaRow(label = "Nombre", value = nombre, onValueChange = { nombre = it })
        CampoEntradaRow(label = "Apellidos", value = apellidos, onValueChange = { apellidos = it })
        CampoEntradaRow(label = "Email", value = email, onValueChange = { email = it })
        CampoEntradaRow(label = "Teléfono", value = telefono, onValueChange = { telefono = it })

        Spacer(modifier = Modifier.height(AppTheme.PaddingL))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = onCerrar) {
                Text("Cancelar")
            }

            Spacer(modifier = Modifier.width(AppTheme.PaddingM))

            Button(onClick = {
                // Guardar cliente desde dentro del formulario
                CoroutineScope(Dispatchers.IO).launch {
                    val cliente = ClienteBO(
                        uuid = 0,
                        nombre = nombre,
                        apellidos = apellidos,
                        dni = "dfg",
                        direccion = "dfg",
                        telefono = telefono.toIntOrNull() ?: 0,
                        email = email,
                        estado = ""
                    )

                    Logs.info(this, "Creando nuevo cliente")
                    clientesApi.crearCliente(cliente)

                    // Cerrar la ventana emergente en el hilo principal
                }

                onCerrar()
            }
            ) {
                Text("Guardar")
            }
        }
    }
}
