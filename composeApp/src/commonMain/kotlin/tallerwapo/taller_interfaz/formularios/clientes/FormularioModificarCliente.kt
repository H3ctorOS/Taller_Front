package tallerwapo.taller_interfaz.formularios.clientes

import androidx.compose.foundation.background
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
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.objetos.campoEntrada.CampoEntradaRow
import tallerwapo.taller_interfaz.objetos.scroll.ScrollableContent
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import tallerwapo.taller_interfaz.objetos.campoEntrada.validaciones.ValidacionesCampoEntrada


@Composable
fun FormularioModificarCliente(
    cliente: ClienteBO,
    onCerrar: () -> Unit
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val clientesRepo = AppContexto.clientesRepo
    val validaciones = ValidacionesCampoEntrada()

    // Estados de los campos
    var nombre by remember { mutableStateOf(cliente.nombre) }
    var apellidos by remember { mutableStateOf(cliente.apellidos) }
    var dni by remember { mutableStateOf(cliente.dni) }
    var direccion by remember { mutableStateOf(cliente.direccion) }
    var email by remember { mutableStateOf(cliente.email) }
    var telefono by remember { mutableStateOf(cliente.telefono.toString()) }

    // ───────── Validación del formulario ─────────
    fun formularioEsValido(): Boolean {
        val nombreValido = nombre.isNotBlank()
        val apellidosValido = apellidos.isNotBlank()
        val dniValido = dni.isBlank() || validaciones.validarDni.funcion(dni)
        val emailValido = email.isBlank() || validaciones.validarEmail.funcion(email)
        val telefonoValido = telefono.isBlank() || validaciones.validarTelefono.funcion(telefono)

        return nombreValido && apellidosValido && dniValido && emailValido && telefonoValido
    }

    Box(
        modifier = Modifier
            .widthIn(max = 800.dp)
            .heightIn(max = 1000.dp)
            .fillMaxHeight(0.8f)
            .padding(theme.paddingS)
            .background(
                color = theme.surfaceColor,
                shape = theme.cornerRadius
            )
    ) {
        ScrollableContent {
            Column(
                modifier = Modifier.padding(theme.paddingM)
            ) {
                Text(
                    text = "Modificar cliente",
                    style = theme.title,
                    modifier = Modifier.padding(bottom = theme.paddingL)
                )

                Spacer(Modifier.height(theme.paddingS))

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

                // ───────── Campos opcionales ─────────
                CampoEntradaRow(
                    titulo = "DNI/NIE",
                    valor = dni,
                    onValueChange = { dni = it },
                    validaciones = listOf(validaciones.validarDni)
                )

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

                Spacer(modifier = Modifier.height(theme.paddingL))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    AppBoton(text = "Cancelar", onClick = { onCerrar() })

                    Spacer(modifier = Modifier.width(theme.paddingM))

                    AppBoton(
                        text = "Modificar",
                        enabled = formularioEsValido(),
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                val clienteModificado = ClienteBO(
                                    uuid = cliente.uuid,
                                    nombre = nombre,
                                    apellidos = apellidos,
                                    dni = dni,
                                    direccion = direccion,
                                    telefono = telefono.toIntOrNull() ?: 0,
                                    email = email,
                                    estado = cliente.estado
                                )
                                Logs.info(this, "Actualizando cliente")
                                val respuesta = clientesRepo.actualizarCliente(clienteModificado)
                                FormulariosService.gestionarRespuestaApi(respuesta) { onCerrar() }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.width(theme.paddingM))

                    AppBoton(
                        text = "Eliminar",
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                Logs.info(this, "Eliminando cliente")
                                val respuesta = clientesRepo.eliminarCliente(cliente)
                                FormulariosService.gestionarRespuestaApi(respuesta) { onCerrar() }
                            }
                        }
                    )
                }
            }
        }
    }
}
