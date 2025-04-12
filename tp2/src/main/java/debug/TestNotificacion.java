package debug;

import servicio.notificacion.NotificacionConsola;
import servicio.notificacion.ServicioNotificaciones;

public class TestNotificacion {
    public static void main(String[] args) {
        ServicioNotificaciones notificador = new NotificacionConsola();
        notificador.enviarNotificacion("Tu recurso ha sido prestado con éxito.");
    }
}
