package tallerwapo.taller_interfaz.formularios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.CampoEntrada.CampoEntradaRow

@Composable
fun FormulatioModificarCliente(
    cliente: ClienteBO,
    onCerrar: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    val clientesRepo = ApiContexto.clientesRepo

    // Estados de los campos
    var nombre by remember { mutableStateOf(cliente.nombre) }
    var apellidos by remember { mutableStateOf(cliente.apellidos) }
    var dni by remember { mutableStateOf(cliente.dni) }
    var direccion by remember { mutableStateOf(cliente.direccion) }
    var email by remember { mutableStateOf(cliente.email) }
    var telefono by remember { mutableStateOf(cliente.telefono.toString()) }

    Column(
        modifier = Modifier
            .widthIn(max = 900.dp)
            .background(theme.surfaceColor, theme.cornerRadius)
            .padding(theme.paddingM)
    ) {
        Text(
            text = "Modificar cliente",
            style = theme.title,
            modifier = Modifier.padding(bottom = theme.paddingL)
        )

        Spacer(Modifier.height(theme.paddingL))

        CampoEntradaRow(titulo = "Nombre", valor = nombre, onValueChange = { nombre = it })
        CampoEntradaRow(titulo = "Apellidos", valor = apellidos, onValueChange = { apellidos = it })
        CampoEntradaRow(titulo = "Dni", valor = dni, onValueChange = { dni = it })
        CampoEntradaRow(titulo = "Direccion", valor = direccion, onValueChange = { direccion = it })
        CampoEntradaRow(titulo = "Email", valor = email, onValueChange = { email = it })
        CampoEntradaRow(titulo = "Teléfono", valor = telefono, onValueChange = { telefono = it })

        Spacer(modifier = Modifier.height(theme.paddingL))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {


            AppBoton(text = "Cancelar",
                onClick = { onCerrar() }
            )

            Spacer(modifier = Modifier.width(theme.paddingM))

            AppBoton( text = "Modificar",
                onClick = { var respuesta: RespuestaDTO<ClienteBO>

                    // Editar cliente desde el formulario
                    CoroutineScope(Dispatchers.IO).launch {
                        val cliente = ClienteBO(
                            uuid = cliente.uuid,
                            nombre = nombre,
                            apellidos = apellidos,
                            dni = dni,
                            direccion = direccion,
                            telefono = telefono.toIntOrNull() ?: 0,
                            email = email,
                            estado = cliente.estado
                        )

                        Logs.info(this, "Actualizando  cliente")

                        val respuesta = clientesRepo.actualizarCliente(cliente)

                        FormulariosService.gestionarRespuestaApi(respuesta){ onCerrar() }
                    }
                })

            Spacer(modifier = Modifier.width(theme.paddingM))

            AppBoton( text = "Eliminar",
                onClick = { var respuesta: RespuestaDTO<ClienteBO>

                    // Eliminar el cliente
                    CoroutineScope(Dispatchers.IO).launch {

                        Logs.info(this, "Eliminando  cliente")

                        val respuesta = clientesRepo.eliminarCliente(cliente)

                        FormulariosService.gestionarRespuestaApi(respuesta){ onCerrar() }
                    }
                })


        }
    }

}