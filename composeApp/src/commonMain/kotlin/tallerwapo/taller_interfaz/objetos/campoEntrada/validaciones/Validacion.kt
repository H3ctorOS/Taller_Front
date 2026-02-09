package tallerwapo.taller_interfaz.objetos.campoEntrada.validaciones

data class Validacion(
    val funcion: (String) -> Boolean,
    val mensajeError: String
)