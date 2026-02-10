package tallerwapo.core.dominio.dto.gestion.servidor

import kotlinx.serialization.Serializable


@Serializable
data class ResumenDatosAppDTO(
    val version: String? = null,
    val cantidadArranques: Int? = null,
    val totalGastos: Double? = null,
    val totalIngresos: Double? = null,
    val totalVehiculos: Int? = null,
    val totalClientes: Int? = null
) {
    /**
     * Devuelve una lista de pares [nombre, valor] solo con los campos que queremos mostrar
     */
    fun getResumenParaBarra(): List<Pair<String, String>> {
        return listOf(
            "Servidor" to (version ?: ""),
            "Arranques" to (cantidadArranques?.toString() ?: ""),
            "Total Gastos" to (totalGastos?.toString() ?: ""),
            "Total Ingresos" to (totalIngresos?.toString() ?: ""),
            "Vehículos" to (totalVehiculos?.toString() ?: ""),
            "Clientes" to (totalClientes?.toString() ?: "")
        )
    }
}