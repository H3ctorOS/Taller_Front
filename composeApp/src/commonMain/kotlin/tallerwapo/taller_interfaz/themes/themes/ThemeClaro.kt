package tallerwapo.taller_interfaz.themes.themes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tallerwapo.taller_interfaz.themes.interfaces.AppTheme

object ThemeClaro : AppTheme {

    // ───────── Colores base (gris WCAG AA) ─────────
    override val backgroundColor = Color(0xFFECEFF3)
    override val surfaceColor = Color(0xFFDFDFE0)
    override val inputBackgroundColor = Color(0xFFB8B5B5)
    override val selectedBackgroundColor = Color(0xFFCBAE20)

    override val textoPrincipalColor = Color(0xFF1F2933)
    override val textoSecundarioColor = Color(0xFF475569)

    override val buttonBackground = Color(0xFF355E75)
    override val buttonContent = Color(0xFFE2E2E2)
    override val buttonSecondary = Color(0xFFCBD2D9)

    // ───────── Tipografía ─────────
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

    // ───────── Formas y espaciados ─────────
    override val cornerRadius = RoundedCornerShape(10.dp)

    override val paddingS = 8.dp
    override val paddingM = 16.dp
    override val paddingL = 24.dp
}
