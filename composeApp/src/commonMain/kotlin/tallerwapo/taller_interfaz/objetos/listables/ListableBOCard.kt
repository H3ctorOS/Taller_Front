package tallerwapo.taller_interfaz.objetos.listables

import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tallerwapo.taller_interfaz.objetos.listables.interfaz.ListableBO


@Composable
fun ListableBOCard(
    item: ListableBO<*>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(item.titulo, style = MaterialTheme.typography.titleMedium)

            item.subtitulo?.let {
                Text(it, style = MaterialTheme.typography.bodyMedium)
            }

            item.descripcion?.let {
                Text(it, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}