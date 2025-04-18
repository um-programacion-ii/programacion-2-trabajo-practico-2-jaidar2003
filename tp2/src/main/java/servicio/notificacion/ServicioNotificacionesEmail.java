package servicio.notificacion;

import interfaz.ServicioNotificaciones;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServicioNotificacionesEmail implements ServicioNotificaciones {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void enviar(String mensaje) {
        executor.submit(() -> {
            System.out.println("📧 [Email] " + mensaje);
            // Simular espera si querés: Thread.sleep(500);
        });
    }

    public void cerrar() {
        executor.shutdown();
        System.out.println("🔌 Servicio de notificaciones por email cerrado.");
    }
}
