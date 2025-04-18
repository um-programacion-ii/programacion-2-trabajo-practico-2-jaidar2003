package servicio.notificacion;

import interfaz.ServicioNotificaciones;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServicioNotificacionesSMS implements ServicioNotificaciones {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void enviar(String mensaje) {
        executor.submit(() -> {
            System.out.println("📲 [SMS] " + mensaje);
        });
    }

    public void cerrar() {
        executor.shutdown();
        System.out.println("🔌 Servicio de notificaciones por SMS cerrado.");
    }
}
