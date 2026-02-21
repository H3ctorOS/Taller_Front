package tallerwapo.taller_interfaz.pantallas.config

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.*
import tallerwapo.core.contexto.AppContexto
import tallerwapo.core.dominio.dto.gestion.servidor.ResumenDatosAppDTO
import tallerwapo.core.servicios.ConfigServices
import tallerwapo.core.servicios.GestionServidorServicios
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.formularios.configuracion.FormularioConfiguracionApp
import tallerwapo.taller_interfaz.objetos.botones.AppBoton
import tallerwapo.taller_interfaz.objetos.emergentes.FormularioEmergente
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.objetos.textos.ItemTexto
import tallerwapo.taller_interfaz.pantallas.principal.componentesMovil.BottomBarMobile
import tallerwapo.taller_interfaz.themes.AppThemeProvider

object ConfigMobileScreen : Screen {

    private enum class EstadoBoton {
        ARRANCAR,
        INTENTANDO_ARRANCAR,
        PARAR,
        VERIFICAR
    }

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val scope = rememberCoroutineScope()
        val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)

        var estadoServidor by remember { mutableStateOf<ResumenDatosAppDTO?>(null) }
        var errorServidor by remember { mutableStateOf(true) }
        var contadorIntentos by remember { mutableStateOf(0) }

        var estadoBoton by remember { mutableStateOf(EstadoBoton.ARRANCAR) }
        var botonProcesando by remember { mutableStateOf(false) }

        var reintentosJob: Job? by remember { mutableStateOf(null) }
        var mostrarConfiguracion by remember { mutableStateOf(false) }

        // 🔹 Verificar servidor con timeout
        suspend fun verificarServidor(): Boolean {

            botonProcesando = true
            contadorIntentos++

            var exito = false

            val resultado = withTimeoutOrNull(5000L) {
                try {
                    val estado = GestionServidorServicios.getEstadoServidor()
                    estadoServidor = estado
                    errorServidor = estado == null
                    exito = estado != null
                } catch (e: Exception) {
                    estadoServidor = null
                    errorServidor = true
                }
            }

            if (resultado == null) {
                estadoServidor = null
                errorServidor = true
                exito = false
            }

            estadoBoton = if (exito) EstadoBoton.VERIFICAR else EstadoBoton.ARRANCAR
            botonProcesando = false
            return exito
        }

        // 🔹 Arrancar servidor
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

                if (estadoBoton == EstadoBoton.INTENTANDO_ARRANCAR) {
                    estadoBoton = EstadoBoton.ARRANCAR
                }
            }
        }

        // 🔹 Parar intentos
        fun pararIntentos() {
            reintentosJob?.cancel()
            estadoBoton = EstadoBoton.ARRANCAR
            botonProcesando = false
        }

        // 🔹 Primera verificación al entrar
        LaunchedEffect(Unit) {
            verificarServidor()
        }

        Scaffold(
            bottomBar = {
                BottomBarMobile() // ✅ corregido: sin argumentos
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                // ===============================
                // 🔹 ESTADO SERVIDOR (VERTICAL)
                // ===============================

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

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
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    ItemTexto("$titulo:", valor, theme)
                                }
                                if (index < resumen.size - 1) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        } ?: run {
                            AppTextos(
                                text = "Cargando estado del servidor...",
                                style = theme.bodyText
                            )
                        }
                    }
                }

                // ===============================
                // 🔹 BOTONES INFERIORES
                // ===============================

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    if (AppContexto.ipServidor != ConfigServices.ipDefecto) {
                        AppBoton(
                            text = "Apagar servidor",
                            onClick = { GestionServidorServicios.apagarServidor() }
                        )
                    }

                    AppBoton(
                        text = "Configuración App",
                        onClick = { mostrarConfiguracion = true }
                    )

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
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }

        // ===============================
        // 🔹 FORMULARIO CONFIGURACIÓN
        // ===============================

        FormularioEmergente(
            mostrar = mostrarConfiguracion,
            onCerrar = { mostrarConfiguracion = false }
        ) {
            FormularioConfiguracionApp(
                onCerrar = { mostrarConfiguracion = false }
            )
        }
    }
}