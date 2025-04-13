package servicio.notificacion;

import interfaz.ServicioNotificaciones;

public class ServicioNotificacionesEmail implements ServicioNotificaciones {
    @Override
    public void enviar(String mensaje) {
        System.out.println("📧 [Email] " + mensaje);
    }
}
