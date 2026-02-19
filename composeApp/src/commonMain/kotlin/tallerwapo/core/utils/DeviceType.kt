package tallerwapo.core.utils

/**
 * Tipos de dispositivo
 */
enum class DeviceType {
    MOBILE,
    TABLET,
    DESKTOP
}

/**
 * Devuelve el tipo de dispositivo actual.
 * Implementación multiplataforma con expect/actual
 */
expect fun getDeviceType(): DeviceType
