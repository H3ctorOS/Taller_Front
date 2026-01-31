package tallerwapo.taller_interfaz.objetos.listables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tallerwapo.core.dominio.bo.interfaz.BaseBO
import tallerwapo.taller_interfaz.objetos.listables.interfaz.ListableBO

@Composable
fun <T : BaseBO, L : ListableBO<T>> ListableBOList(
    items: List<L>,
    selectedItemId: Int?,
    onItemClick: (T) -> Unit,
    onItemDoubleClick: ((T) -> Unit)? = null,  // nuevo,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier
    ){
        items(items, key = { it.bo.uuid }) { item ->
            val isSelected = item.bo.uuid == selectedItemId

            ListableBOCard(
                item = item,
                isSelected = isSelected,
                onClick = { onItemClick(item.bo) },
                onDoubleClick = { onItemDoubleClick?.invoke(item.bo) }
            )
        }
    }
}