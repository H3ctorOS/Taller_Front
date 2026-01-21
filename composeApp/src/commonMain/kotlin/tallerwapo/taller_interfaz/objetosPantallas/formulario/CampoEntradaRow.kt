package com.tallerwapo.interfazgrafica.taller_interfazgrafica.objetosPantallas.formulario

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//Estilos de fuentes
val TAMANIOA_FUENTE_CAMPOS  = 20.sp;
val ESTILO_FUENTE = FontStyle.Italic;
val ESTILO_TEXTO_CAMPOS = TextStyle.Default.copy(fontSize = TAMANIOA_FUENTE_CAMPOS);

//Colores
val COLOR_ETIQUETAS = Color.White;


@Composable
fun CampoEntradaRow(label: String, value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = COLOR_ETIQUETAS,
            fontStyle = ESTILO_FUENTE,
            fontSize = TAMANIOA_FUENTE_CAMPOS,
            modifier = modifier.width(160.dp),
            textAlign = TextAlign.End
        )

        Spacer(Modifier.width(8.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = ESTILO_TEXTO_CAMPOS,
            modifier = Modifier.weight(1f).padding(8.dp)
        )
    }
}