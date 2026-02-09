package tallerwapo.taller_interfaz.objetos.campoEntrada.validaciones

class ValidacionesCampoEntrada {

    // ─── Validación de IP ───
    val validarIp = Validacion(
        funcion = { valor ->
            val ipRegex = Regex("""^((25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)(\.|$)){4}$|^localhost$""")
            ipRegex.matches(valor)
        },
        mensajeError = "La IP no tiene un formato válido. Ejemplo: 192.168.1.100 o localhost"
    )

    // ─── Validación de MAC ───
    val validarMac = Validacion(
        funcion = { valor ->
            val macRegex = Regex("""^([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$""")
            macRegex.matches(valor)
        },
        mensajeError = "La MAC no tiene un formato válido. Ejemplo: 00:11:22:33:44:55"
    )

    // ─── Validación de texto no vacío ───
    val validarNoVacio = Validacion(
        funcion = { valor -> valor.isNotBlank() },
        mensajeError = "Este campo no puede estar vacío"
    )

    // ─── Validación de Email ───
    val validarEmail = Validacion(
        funcion = { valor ->
            val emailRegex = Regex("""^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$""")
            emailRegex.matches(valor)
        },
        mensajeError = "El email no tiene un formato válido. Ejemplo: usuario@dominio.com"
    )

    // ─── Validación de Teléfono ───
    val validarTelefono = Validacion(
        funcion = { valor ->
            val telRegex = Regex("""^\+?[0-9]{7,15}$""")
            telRegex.matches(valor)
        },
        mensajeError = "El teléfono no tiene un formato válido. Ejemplo: +34123456789 o 123456789"
    )

    val validarMatriculaEspañola = Validacion(
        funcion = { valor ->
            val matricula = valor.trim().uppercase()

            // Formato moderno: 4 números + 3 letras
            val moderno = Regex("""^\d{4}\s?[B-DF-HJ-NP-TV-Z]{3}$""")

            // Formato antiguo: 1-2 letras provincia + 1-4 números + 1-2 letras finales
            val antiguo = Regex("""^[A-Z]{1,2}\s?\d{1,4}\s?[A-Z]{1,2}$""")

            moderno.matches(matricula) || antiguo.matches(matricula)
        },
        mensajeError = "La matrícula no tiene un formato válido. Ejemplo moderno: 1234 ABC, antiguo: M 1234 AB"
    )
}
