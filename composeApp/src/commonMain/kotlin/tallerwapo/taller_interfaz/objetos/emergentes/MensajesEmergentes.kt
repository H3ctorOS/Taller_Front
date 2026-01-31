package tallerwapo.taller_interfaz.objetos.emergentes


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.themes.AppThemeProvider


object MensajesEmergentes {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

    private var dialogoActivo by mutableStateOf(false)
    private var titulo by mutableStateOf("")
    private var mensaje by mutableStateOf("")
    private var botones by mutableStateOf<List<BotonDialogo>>(emptyList())

    data class BotonDialogo(
        val texto: String,
        val accion: () -> Unit = {}
    )

    /** Mostrar un diálogo */
    fun mostrarDialogo(
        titulo: String,
        mensaje: String,
        botones: List<BotonDialogo>
    ) {
        this.titulo = titulo
        this.mensaje = mensaje
        this.botones = botones
        dialogoActivo = true
    }

    /** Composable que se coloca en la raíz de la app */
    @Composable
    fun Contenido() {
        if (dialogoActivo) {
            AlertDialog(
                onDismissRequest = { /* no cerrar tocando fuera */ },
                title = { Text(text = titulo, color = theme.textoPrincipalColor) },
                text = { Text(text = mensaje, color = theme.textoPrincipalColor) },
                confirmButton = {

                    // Usamos el primer botón como confirm
                    if (botones.isNotEmpty()) {
                        AppBoton(text = botones[0].texto,
                            onClick = {
                                botones[0].accion()
                                dialogoActivo = false
                            }
                        )
                    }
                },
                dismissButton = {
                    // Si hay más de un botón, el segundo se considera "cancel"
                    if (botones.size > 1) {

                        AppBoton(text = botones[1].texto,
                            onClick = {  botones[1].accion()
                                dialogoActivo = false }
                        )

                    }
                }
            )
        }
    }
}