package tallerwapo.taller_interfaz.pantallas.principal.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import tallerwapo.core.dominio.dto.gestion.servidor.ResumenDatosAppDTO
import tallerwapo.core.servicios.GestionServidorServicios
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.objetos.separadores.EspacioHorizontal
import tallerwapo.taller_interfaz.objetos.separadores.SeparadorVertical
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.objetos.textos.ItemTexto
import tallerwapo.taller_interfaz.themes.AppThemeProvider

object BarraInferior {

    private enum class EstadoBoton {
        ARRANCAR,
        INTENTANDO_ARRANCAR,
        PARAR,
        VERIFICAR
    }

    @Composable
    fun Contenido() {
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
        val scope = rememberCoroutineScope()

        var estadoServidor by remember { mutableStateOf<ResumenDatosAppDTO?>(null) }
        var errorServidor by remember { mutableStateOf(true) }
        var contadorIntentos by remember { mutableStateOf(0) }

        var estadoBoton by remember { mutableStateOf(EstadoBoton.ARRANCAR) }
        var botonProcesando by remember { mutableStateOf(false) }

        var reintentosJob: Job? by remember { mutableStateOf(null) }

        // Función para verificar servidor con timeout de 5s
        suspend fun verificarServidor(): Boolean {
            botonProcesando = true
            contadorIntentos += 1
            var exito = false

            val resultado = withTimeoutOrNull(5000L) {
                try {
                    val estado = GestionServidorServicios.getEstadoServidor()
                    estadoServidor = estado
                    errorServidor = estado == null
                    exito = estado != null
                } catch (e: Exception) {
                    println("Error al obtener estado del servidor: $e")
                    estadoServidor = null
                    errorServidor = true
                }
            }

            if (resultado == null) {
                errorServidor = true
                estadoServidor = null
                exito = false
            }

            // Actualiza estado del botón según resultado
            estadoBoton = when {
                exito -> EstadoBoton.VERIFICAR
                estadoBoton != EstadoBoton.INTENTANDO_ARRANCAR &&
                        estadoBoton != EstadoBoton.PARAR -> EstadoBoton.ARRANCAR
                else -> estadoBoton
            }

            botonProcesando = false
            return exito
        }

        // Arrancar servidor y ciclo automático de verificación cada 5s
        fun iniciarIntentosArranque() {
            estadoBoton = EstadoBoton.INTENTANDO_ARRANCAR
            reintentosJob?.cancel()
            reintentosJob = scope.launch {
                botonProcesando = true
                GestionServidorServicios.arrancarServidor()
                botonProcesando = false

                while (estadoBoton == EstadoBoton.INTENTANDO_ARRANCAR) {
                    val exito = verificarServidor()
                    if (exito) break
                    delay(5000L)
                }

                // Si finaliza ciclo sin éxito, volver a ARRANCAR
                if (estadoBoton == EstadoBoton.INTENTANDO_ARRANCAR) {
                    estadoBoton = EstadoBoton.ARRANCAR
                }
            }
        }

        // Parar ciclo de arranque
        fun pararIntentos() {
            reintentosJob?.cancel()
            estadoBoton = EstadoBoton.ARRANCAR
            botonProcesando = false
        }

        // Primera verificación al iniciar
        LaunchedEffect(Unit) {
            verificarServidor()
        }

        // Verificación periódica si servidor activo
        LaunchedEffect(estadoBoton) {
            if (estadoBoton == EstadoBoton.VERIFICAR) {
                while (estadoBoton == EstadoBoton.VERIFICAR) {
                    delay(300_000L) // 5 minutos
                    val exito = verificarServidor()
                    if (!exito) estadoBoton = EstadoBoton.ARRANCAR
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(theme.surfaceColor),
            contentAlignment = Alignment.Center
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = theme.paddingL)
            ) {

                // Texto de estado
                if (errorServidor) {
                    AppTextos(
                        text = "Servidor posiblemente no arrancado (Intentos: $contadorIntentos)",
                        style = theme.bodyText.copy(color = Color.Red)
                    )
                } else {
                    estadoServidor?.let { servidor ->
                        val resumen = servidor.getResumenParaBarra()
                        resumen.forEachIndexed { index, (titulo, valor) ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 2.dp)
                            ) {
                                ItemTexto("$titulo:", valor, theme)
                            }

                            if (index < resumen.size - 1) {
                                EspacioHorizontal(theme)
                                SeparadorVertical(theme)
                                EspacioHorizontal(theme)
                            }
                        }
                    } ?: run {
                        AppTextos(
                            text = "Cargando estado del servidor...",
                            style = theme.bodyText
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f)) // Empuja el botón a la derecha

                // Botón dinámico
                AppBoton(
                    text = when (estadoBoton) {
                        EstadoBoton.ARRANCAR -> "Arrancar servidor"
                        EstadoBoton.INTENTANDO_ARRANCAR -> "Intentando arrancar..."
                        EstadoBoton.PARAR -> "Parar"
                        EstadoBoton.VERIFICAR -> "Verificar estado"
                    },
                    enabled = !botonProcesando,
                    onClick = {
                        when (estadoBoton) {
                            EstadoBoton.ARRANCAR -> iniciarIntentosArranque()
                            EstadoBoton.INTENTANDO_ARRANCAR -> estadoBoton = EstadoBoton.PARAR
                            EstadoBoton.PARAR -> pararIntentos()
                            EstadoBoton.VERIFICAR -> scope.launch { verificarServidor() }
                        }
                    }
                )

                if (botonProcesando) {
                    EspacioHorizontal(theme)
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}
