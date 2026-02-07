package tallerwapo.taller_interfaz.formularios.clientes

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.ApiContexto
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.servicios.FormulariosService
import tallerwapo.core.utils.Logs
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.campoEntrada.CampoEntradaRow
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.themes.AppThemeProvider


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment

@Composable
fun FormularioNuevoCliente(
    onCerrar: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    val clientesRepo = ApiContexto.clientesRepo

    // Estados de los campos
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }


    // Scroll state para la columna
    val scrollState = rememberScrollState()


    // ───────── Validación del formulario ─────────
    fun formularioEsValido(): Boolean {
        return apellidos.isNotBlank() &&
                nombre.isNotBlank()
    }



    Box(
        modifier = Modifier
            .widthIn(max = 800.dp)
            .heightIn(max = 900.dp) // <-- Altura máxima
            .background(theme.surfaceColor, theme.cornerRadius)
            .padding(theme.paddingS)
            .fillMaxSize()
    ) {
        // Columna scrollable
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(theme.paddingM)
        ) {
            Text(
                text = "Nuevo Cliente",
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
            CampoEntradaRow(titulo = "Observaciones", valor = observaciones, onValueChange = { observaciones = it })

            Spacer(modifier = Modifier.height(theme.paddingL))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                AppBoton(text = "Cancelar", onClick = { onCerrar() })

                Spacer(modifier = Modifier.width(theme.paddingM))

                AppBoton(
                    text = "Guardar",
                    enabled = formularioEsValido(),
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val cliente = ClienteBO(
                                uuid = 0,
                                nombre = nombre,
                                apellidos = apellidos,
                                dni = dni,
                                direccion = direccion,
                                telefono = telefono.toIntOrNull() ?: 0,
                                email = email,
                                estado = "",
                                observaciones = observaciones,
                            )
                            Logs.info(this, "Creando nuevo cliente")
                            val respuesta = clientesRepo.crearCliente(cliente)
                            FormulariosService.gestionarRespuestaApi(respuesta) { onCerrar() }
                        }
                    }
                )
            }
        }

        // Barra de scroll
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
        )
    }
}