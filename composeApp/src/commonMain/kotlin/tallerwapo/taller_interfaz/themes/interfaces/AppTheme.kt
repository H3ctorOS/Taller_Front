package tallerwapo.taller_interfaz.themes.interfaces

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

interface AppTheme {

    // Colores base
    val backgroundColor: Color
    val surfaceColor: Color
    val inputBackgroundColor: Color
    val selectedBackgroundColor: Color


    val textoPrincipalColor: Color
    val textoSecundarioColor: Color

    val buttonBackground: Color
    val buttonContent: Color
    val buttonSecondary: Color

    // Tipografía
    val title: TextStyle
    val subTitleText: TextStyle
    val bodyText: TextStyle
    val input: TextStyle
    val botonText: TextStyle


    // Formas
    val cornerRadius: RoundedCornerShape

    // Espaciados
    val paddingS: Dp
    val paddingM: Dp
    val paddingL: Dp
}