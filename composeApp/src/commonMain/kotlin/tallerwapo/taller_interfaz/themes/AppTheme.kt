package tallerwapo.taller_interfaz.themes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object AppTheme {

    // ───────── Colores prueba ─────────
    val rojo = Color(0xFF921616)


    // ───────── Colores base (tema claro) ─────────
    val Background = Color(0xFFF5F6F8)      // fondo general (gris claro)
    val Surface = Color(0xFFFFFFFF)         // panels, cards
    val InputBackground = Color(0xFFF0F1F3) // campos de entrada

    val TextoPrincipal = Color(0xFF1F1F1F)     // texto principal
    val TextoSecundario = Color(0xFF5F6368)   // texto secundario

    val ButtonBackground = Color(0xFF9FBECE)
    val ButtonContent = Color.White
    val ButtonSecondary = Color(0xFF9DB4C0)



    // ───────── Tipografía ─────────
    val Title = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextoPrincipal
    )

    val SubTitle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextoPrincipal
    )

    val Body = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = TextoPrincipal
    )

    val Input = TextStyle(
        fontSize = 16.sp,
        color = `TextoPrincipal`
    )

    // ───────── Formas ─────────
    val CornerRadius = RoundedCornerShape(10.dp)


    // ───────── Espaciados ─────────
    val PaddingS = 8.dp
    val PaddingM = 16.dp
    val PaddingL = 24.dp
}
