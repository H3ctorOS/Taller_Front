package tallerwapo.taller_interfaz.formularios

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
import tallerwapo.core.dominio.dto.RespuestaDTO
import tallerwapo.core.utils.FormulariosService
import tallerwapo.core.utils.Logs
import tallerwapo.taller_interfaz.objetos.CampoEntrada.CampoEntradaRow
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.themes.AppTheme
@Composable
fun FormularioNuevoCliente(
    onCerrar: () -> Unit
) {
    val clientesApi = ApiContexto.clientesApi

    // Estados de los campos
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
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

        CampoEntradaRow(titulo = "Nombre", valor = nombre, onValueChange = { nombre = it })
        CampoEntradaRow(titulo = "Apellidos", valor = apellidos, onValueChange = { apellidos = it })
        CampoEntradaRow(titulo = "Dni", valor = dni, onValueChange = { dni = it })
        CampoEntradaRow(titulo = "Direccion", valor = direccion, onValueChange = { direccion = it })
        CampoEntradaRow(titulo = "Email", valor = email, onValueChange = { email = it })
        CampoEntradaRow(titulo = "Teléfono", valor = telefono, onValueChange = { telefono = it })

        Spacer(modifier = Modifier.height(AppTheme.PaddingL))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {


            AppBoton(text = "Cancelar",
                onClick = { onCerrar() }
            )

            Spacer(modifier = Modifier.width(AppTheme.PaddingM))

            AppBoton( text = "Guardar",
                onClick = { var respuesta: RespuestaDTO<ClienteBO>

                    // Guardar cliente desde dentro del formulario
                    CoroutineScope(Dispatchers.IO).launch {
                        val cliente = ClienteBO(
                            uuid = 0,
                            nombre = nombre,
                            apellidos = apellidos,
                            dni = dni,
                            direccion = direccion,
                            telefono = telefono.toIntOrNull() ?: 0,
                            email = email,
                            estado = ""
                        )

                        Logs.info(this, "Creando nuevo cliente")

                        val respuesta = clientesApi.crearCliente(cliente)
                        FormulariosService.gestionarRespuestaApi(respuesta){ onCerrar() }
                    }
                })


        }
    }
}
