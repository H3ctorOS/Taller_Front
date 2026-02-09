package tallerwapo.taller_interfaz.objetos.campoEntrada.validaciones

class ValidacionesCampoEntrada {

    // ─── Validación de número ───
    val validarNumero = Validacion(
        funcion = { valor ->
            valor.isBlank() || valor.toDoubleOrNull() != null
        },
        mensajeError = "Debe introducir un número válido"
    )
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

    // ─── Validación de Email ───
    val validarEmail = Validacion(
        funcion = { valor ->
            val emailRegex = Regex("""^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$""")
            valor.isBlank() || emailRegex.matches(valor)  // Permite vacío si no es obligatorio
        },
        mensajeError = "El email no tiene un formato válido. Ejemplo: usuario@dominio.com"
    )

    // ─── Validación de Teléfono ───
    val validarTelefono = Validacion(
        funcion = { valor ->
            val telRegex = Regex("""^\+?[0-9]{7,15}$""")
            valor.isBlank() || telRegex.matches(valor)  // Permite vacío si no es obligatorio
        },
        mensajeError = "El teléfono no tiene un formato válido. Ejemplo: +34123456789 o 123456789"
    )

    // ─── Validación de matrículas españolas ───
    val validarMatriculaEspañola = Validacion(
        funcion = { valor ->
            val matricula = valor.trim().uppercase()
            val moderno = Regex("""^\d{4}\s?[B-DF-HJ-NP-TV-Z]{3}$""")
            val antiguo = Regex("""^[A-Z]{1,2}\s?\d{1,4}\s?[A-Z]{1,2}$""")
            moderno.matches(matricula) || antiguo.matches(matricula) || valor.isBlank()
        },
        mensajeError = "La matrícula no tiene un formato válido. Ejemplo moderno: 1234 ABC, antiguo: M 1234 AB"
    )

    // ─── Validación de DNI/NIE ───
    val validarDni = Validacion(
        funcion = { valor ->
            val dniRegex = Regex("""^\d{8}[A-Za-z]$""")
            val nieRegex = Regex("""^[XYZ]\d{7}[A-Za-z]$""")
            valor.isBlank() || dniRegex.matches(valor) || nieRegex.matches(valor)
        },
        mensajeError = "El DNI/NIE no tiene un formato válido (DNI: 12345678A, NIE: X1234567L)"
    )
}
