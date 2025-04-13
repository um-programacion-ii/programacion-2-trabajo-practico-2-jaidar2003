package servicio.notificacion;

import interfaz.ServicioNotificaciones;

public class ServicioNotificacionesSMS implements ServicioNotificaciones {
    @Override
    public void enviar(String mensaje) {
        System.out.println("📲 [SMS] " + mensaje);
    }
}
