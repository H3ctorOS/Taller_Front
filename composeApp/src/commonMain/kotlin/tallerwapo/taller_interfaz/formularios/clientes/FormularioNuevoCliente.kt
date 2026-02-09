package tallerwapo.taller_interfaz.formularios.clientes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tallerwapo.core.contexto.AppContexto
import tallerwapo.core.dominio.bo.ClienteBO
import tallerwapo.core.servicios.FormulariosService
import tallerwapo.core.utils.Logs
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.campoEntrada.CampoEntradaRow
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.objetos.scroll.ScrollableContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import tallerwapo.taller_interfaz.objetos.campoEntrada.validaciones.ValidacionesCampoEntrada

@Composable
fun FormularioNuevoCliente(
    onCerrar: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val clientesRepo = AppContexto.clientesRepo
    val validaciones = ValidacionesCampoEntrada()

    // Estados de los campos
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }

    // ───────── Validación del formulario ─────────
    fun formularioEsValido(): Boolean {
        // Se valida el campo obligatorio y las validaciones específicas
        val nombreValido = nombre.isNotBlank()
        val apellidosValido = apellidos.isNotBlank()
        val dniValido = dni.isBlank() || validaciones.validarDni.funcion(dni)
        val emailValido = email.isBlank() || validaciones.validarEmail.funcion(email) // email opcional
        val telefonoValido = telefono.isBlank() || validaciones.validarTelefono.funcion(telefono) // teléfono opcional

        return nombreValido && apellidosValido && dniValido && emailValido && telefonoValido
    }

    Box(
        modifier = Modifier
            .widthIn(max = 800.dp)
            .heightIn(max = 900.dp)
            .background(theme.surfaceColor, theme.cornerRadius)
            .padding(theme.paddingS)
            .fillMaxSize()
    ) {
        ScrollableContent {
            Column(modifier = Modifier.padding(theme.paddingM)) {
                Text(
                    text = "Nuevo Cliente",
                    style = theme.title,
                    modifier = Modifier.padding(bottom = theme.paddingL)
                )

                Spacer(Modifier.height(theme.paddingL))

                // ───────── Campos obligatorios ─────────
                CampoEntradaRow(
                    titulo = "Nombre",
                    valor = nombre,
                    onValueChange = { nombre = it },
                    obligatorio = true
                )

                CampoEntradaRow(
                    titulo = "Apellidos",
                    valor = apellidos,
                    onValueChange = { apellidos = it },
                    obligatorio = true
                )

                CampoEntradaRow(
                    titulo = "DNI/NIE",
                    valor = dni,
                    onValueChange = { dni = it },
                    validaciones = listOf(validaciones.validarDni)
                )

                // ───────── Campos opcionales ─────────
                CampoEntradaRow(
                    titulo = "Dirección",
                    valor = direccion,
                    onValueChange = { direccion = it }
                )

                CampoEntradaRow(
                    titulo = "Email",
                    valor = email,
                    onValueChange = { email = it },
                    validaciones = listOf(validaciones.validarEmail)
                )

                CampoEntradaRow(
                    titulo = "Teléfono",
                    valor = telefono,
                    onValueChange = { telefono = it },
                    validaciones = listOf(validaciones.validarTelefono)
                )

                CampoEntradaRow(
                    titulo = "Observaciones",
                    valor = observaciones,
                    onValueChange = { observaciones = it }
                )

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
        }
    }
}

