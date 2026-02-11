package tallerwapo.core.dominio.dto.calendario

import kotlinx.serialization.Serializable

@Serializable
data class SemanasDelAnioDTO(
    val semanasPorMes: Map<Int, List<SemanaSelectorDTO>>
)