package tallerwapo.taller_interfaz

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform