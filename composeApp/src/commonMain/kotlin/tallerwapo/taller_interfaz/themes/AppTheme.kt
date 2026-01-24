package tallerwapo.taller_interfaz.themes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object AppTheme {

    // ───────── Colores base (tema claro) ─────────
    val Background = Color(0xFFF5F6F8)      // fondo general (gris claro)
    val Surface = Color(0xFFFFFFFF)         // panels, cards
    val InputBackground = Color(0xFFF0F1F3) // campos de entrada

    val TextPrimary = Color(0xFF1F1F1F)     // texto principal
    val TextSecondary = Color(0xFF5F6368)   // texto secundario

    val Accent = Color(0xFF1976D2)          // azul profesional
    val Error = Color(0xFFD32F2F)
    val Success = Color(0xFF388E3C)

    // ───────── Tipografía ─────────
    val Title = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextPrimary
    )

    val Body = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = TextPrimary
    )

    val Input = TextStyle(
        fontSize = 16.sp,
        color = TextPrimary
    )

    // ───────── Formas ─────────
    val CornerRadius = RoundedCornerShape(8.dp)

    // ───────── Espaciados ─────────
    val PaddingS = 8.dp
    val PaddingM = 16.dp
    val PaddingL = 24.dp
}
