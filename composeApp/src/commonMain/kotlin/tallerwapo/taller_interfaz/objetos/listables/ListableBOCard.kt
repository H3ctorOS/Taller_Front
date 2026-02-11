package tallerwapo.taller_interfaz.objetos.listables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.listables.interfaz.ListableBO
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider

@Composable
fun ListableBOCard(
    item: ListableBO<*>,
    onClick: () -> Unit,
    isSelected: Boolean,
    onDoubleClick: (() -> Unit)? = null,
    doubleClickDelay: Long = 300L
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val scope = rememberCoroutineScope()
    var lastClickTime by remember { mutableStateOf(0L) }
    var expandido by remember { mutableStateOf(false) }

    val backgroundColor = if (isSelected) {
        theme.selectedBackgroundColor.copy(alpha = 0.15f)
    } else {
        theme.surfaceColor
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(theme.paddingS)
            .background(color = backgroundColor, shape = theme.cornerRadius)
            .clickable {
                val currentTime = System.currentTimeMillis()
                if (onDoubleClick != null && currentTime - lastClickTime <= doubleClickDelay) {
                    onDoubleClick()
                    lastClickTime = 0L
                } else {
                    scope.launch {
                        val thisClickTime = currentTime
                        delay(doubleClickDelay)
                        if (lastClickTime == thisClickTime) {
                            expandido = !expandido
                            onClick()
                        }
                    }
                    lastClickTime = currentTime
                }
            }
            .animateContentSize()
            .padding(theme.paddingS),
        verticalAlignment = Alignment.Top
    ) {

        if (item.contenidoExtra != null) {
            // ------------------ CONTENIDO IZQUIERDO CON contenidoExtra ------------------
            Column(
                modifier = Modifier.weight(1f)
            ) {
                AppTextos(text = item.titulo, style = theme.subTitleText)

                item.subtitulo?.let {
                    Spacer(Modifier.height(theme.paddingS))
                    AppTextos(text = it, style = theme.bodyText)
                }

                item.descripcion?.let {
                    Spacer(Modifier.height(theme.paddingS))
                    AppTextos(text = it, style = theme.bodyText)
                }

                if (expandido) {
                    Spacer(Modifier.height(theme.paddingS))
                    item.ContenidoDesplegable()
                }
            }

            // ------------------ CONTENIDO EXTRA DERECHA ------------------
            Spacer(Modifier.width(theme.paddingS))
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ) {
                //item.contenidoExtra()
            }

        } else {
            // ------------------ CONTENIDO PRINCIPAL CENTRADO ------------------
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppTextos(text = item.titulo, style = theme.subTitleText)

                item.subtitulo?.let {
                    Spacer(Modifier.height(theme.paddingS))
                    AppTextos(text = it, style = theme.bodyText)
                }

                item.descripcion?.let {
                    Spacer(Modifier.height(theme.paddingS))
                    AppTextos(text = it, style = theme.bodyText)
                }

                if (expandido) {
                    Spacer(Modifier.height(theme.paddingS))
                    item.ContenidoDesplegable()
                }
            }
        }
    }
}
