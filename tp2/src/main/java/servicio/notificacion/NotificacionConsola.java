package servicio.notificacion;

/**
 * Implementación de ServicioNotificaciones que envía las notificaciones a la consola.
 */
public class NotificacionConsola implements ServicioNotificaciones {

    @Override
    public void enviarNotificacion(String mensaje) {
        System.out.println("🔔 Notificación: " + mensaje);
    }
}
