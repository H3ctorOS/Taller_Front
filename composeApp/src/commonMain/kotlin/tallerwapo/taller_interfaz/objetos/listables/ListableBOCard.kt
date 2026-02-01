package tallerwapo.taller_interfaz.objetos.listables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import tallerwapo.taller_interfaz.InterfazContext
import tallerwapo.taller_interfaz.objetos.listables.interfaz.ListableBO
import tallerwapo.taller_interfaz.objetos.textos.AppTextos
import tallerwapo.taller_interfaz.themes.AppThemeProvider
import kotlinx.coroutines.delay


@Composable
fun ListableBOCard(
    item: ListableBO<*>,
    onClick: () -> Unit,
    isSelected: Boolean,
    onDoubleClick: (() -> Unit)? = null,   // nuevo parámetro opcional
    doubleClickDelay: Long = 300L  // tiempo máximo entre clicks para doble clic
) {
    val theme = AppThemeProvider.getTheme(InterfazContext.themeMode)
    val scope = rememberCoroutineScope()
    var lastClickTime by remember { mutableStateOf(0L) }


    val backgroundColor = if (isSelected) {
        theme.selectedBackgroundColor.copy(alpha = 0.15f)   // color cuando está seleccionada
    } else {
        theme.surfaceColor                       // color normal
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(theme.paddingS)
            .background(color = backgroundColor, shape = theme.cornerRadius)
            .clickable {
                val currentTime = System.currentTimeMillis()

                if (onDoubleClick != null && currentTime - lastClickTime <= doubleClickDelay) {
                    // Doble clic detectado
                    onDoubleClick()
                    lastClickTime = 0L
                } else {
                    // Click simple, espera a ver si viene un segundo clic
                    scope.launch {
                        val thisClickTime = currentTime
                        delay(doubleClickDelay)
                        if (lastClickTime == thisClickTime) {
                            onClick()
                        }
                    }
                    lastClickTime = currentTime
                }
            }
        ,horizontalAlignment = Alignment.CenterHorizontally

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

        Spacer(Modifier.height(theme.paddingS))
    }
}