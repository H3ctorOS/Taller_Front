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

    // Estado para controlar si el card está expandido
    var expandido by remember { mutableStateOf(false) }

    val backgroundColor = if (isSelected) {
        theme.selectedBackgroundColor.copy(alpha = 0.15f)
    } else {
        theme.surfaceColor
    }

    Column(
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
                            // Click simple: alterna expandido
                            expandido = !expandido
                            onClick()
                        }
                    }
                    lastClickTime = currentTime
                }
            }
            .animateContentSize(), // animación suave al expandir/colapsar
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(theme.paddingS))

        AppTextos(text = item.titulo, style = theme.subTitleText)

        item.subtitulo?.let {
            Spacer(Modifier.height(theme.paddingS))
            AppTextos(text = it, style = theme.bodyText)
        }

        item.descripcion?.let {
            Spacer(Modifier.height(theme.paddingS))
            AppTextos(text = it, style = theme.bodyText)
        }

        // Contenido desplegable
        if (expandido) {
            Spacer(Modifier.height(theme.paddingS))
            item.ContenidoDesplegable()
        }

        Spacer(Modifier.height(theme.paddingS))
    }
}
