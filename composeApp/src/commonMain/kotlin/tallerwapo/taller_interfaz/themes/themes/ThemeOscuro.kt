package tallerwapo.taller_interfaz.themes.themes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tallerwapo.taller_interfaz.themes.interfaces.AppTheme

object ThemeOscuro : AppTheme {

    override val backgroundColor = Color(0xFF121212)
    override val surfaceColor = Color(0xFF1E1E1E)
    override val inputBackgroundColor = Color(0xFF2A2A2A)
    override val selectedBackgroundColor = Color(0xFFB8B5B5)

    override val textoPrincipalColor = Color(0xFFEDEDED)
    override val textoSecundarioColor = Color(0xFFAAAAAA)

    override val buttonBackground = Color(0xFF3A6D8C)
    override val buttonContent = Color.White
    override val buttonSecondary = Color(0xFF4A4A4A)

    override val title = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = textoPrincipalColor
    )

    override val subTitleText = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = textoPrincipalColor
    )

    override val bodyText = TextStyle(
        fontSize = 14.sp,
        color = textoPrincipalColor
    )

    override val input = TextStyle(
        fontSize = 16.sp,
        color = textoPrincipalColor
    )

    override val botonText = TextStyle(
        fontSize = 14.sp,
        color = buttonContent
    )

    override val cornerRadius = RoundedCornerShape(10.dp)

    override val paddingS = 8.dp
    override val paddingM = 16.dp
    override val paddingL = 24.dp
}