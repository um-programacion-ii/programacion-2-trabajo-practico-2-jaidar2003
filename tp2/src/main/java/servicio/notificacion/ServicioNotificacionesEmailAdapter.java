package servicio.notificacion;

import interfaz.ServicioNotificaciones;

/**
 * Adaptador para ServicioNotificacionesEmail que lo hace compatible con la interfaz
 * ServicioNotificaciones del paquete servicio.notificacion.
 */
public class ServicioNotificacionesEmailAdapter implements servicio.notificacion.ServicioNotificaciones {
    private final ServicioNotificacionesEmail servicioEmail;

    public ServicioNotificacionesEmailAdapter(ServicioNotificacionesEmail servicioEmail) {
        this.servicioEmail = servicioEmail;
    }

    @Override
    public void enviarNotificacion(String mensaje) {
        servicioEmail.enviar(mensaje);
    }

    public void cerrar() {
        servicioEmail.cerrar();
    }
}
