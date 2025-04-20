package test;

import modelo.CategoriaRecurso;
import modelo.PreferenciasNotificacion;
import modelo.Recordatorio;
import modelo.Usuario;
import recurso.Libro;
import servicio.notificacion.NotificacionConsola;
import servicio.notificacion.ServicioNotificaciones;
import servicio.notificacion.ServicioNotificacionesEmail;
import servicio.notificacion.ServicioNotificacionesEmailAdapter;
import servicio.notificacion.ServicioNotificacionesSMS;

/**
 * Test para el sistema de notificaciones que prueba diferentes servicios de notificación,
 * adaptadores, y preferencias de usuario.
 */
public class TestNotificacion {
    public static void main(String[] args) {
        System.out.println("\n🔍 TEST: Sistema de Notificaciones");
        System.out.println("===============================");

        // Prueba 1: Notificaciones por consola
        System.out.println("\n🧪 Prueba 1: Notificaciones por consola");
        ServicioNotificaciones notificadorConsola = new NotificacionConsola();

        System.out.println("📤 Enviando notificación informativa:");
        notificadorConsola.enviarNotificacion("Tu recurso ha sido prestado con éxito.");

        System.out.println("📤 Enviando notificación de advertencia:");
        notificadorConsola.enviarNotificacion("Tu préstamo vence en 2 días.");

        System.out.println("📤 Enviando notificación de error:");
        notificadorConsola.enviarNotificacion("No se pudo procesar tu solicitud de renovación.");

        // Prueba 2: Notificaciones por email
        System.out.println("\n🧪 Prueba 2: Notificaciones por email");
        ServicioNotificacionesEmail servicioEmail = new ServicioNotificacionesEmail();

        System.out.println("📧 Enviando email informativo:");
        servicioEmail.enviar("Bienvenido al sistema de biblioteca.");

        System.out.println("📧 Enviando email de confirmación:");
        servicioEmail.enviar("Tu reserva ha sido confirmada.");

        // Prueba 3: Notificaciones por SMS
        System.out.println("\n🧪 Prueba 3: Notificaciones por SMS");
        ServicioNotificacionesSMS servicioSMS = new ServicioNotificacionesSMS();

        System.out.println("📱 Enviando SMS de recordatorio:");
        servicioSMS.enviar("Recordatorio: Devolver libro mañana.");

        System.out.println("📱 Enviando SMS de alerta:");
        servicioSMS.enviar("Alerta: Tu libro está vencido.");

        // Prueba 4: Uso del adaptador de email
        System.out.println("\n🧪 Prueba 4: Uso del adaptador de email");
        ServicioNotificacionesEmailAdapter adaptadorEmail = new ServicioNotificacionesEmailAdapter(servicioEmail);

        System.out.println("📧 Enviando notificación a través del adaptador:");
        adaptadorEmail.enviarNotificacion("Este mensaje usa el adaptador de email.");

        // Prueba 5: Notificaciones según preferencias de usuario
        System.out.println("\n🧪 Prueba 5: Notificaciones según preferencias de usuario");

        // Usuario con todas las notificaciones activadas (por defecto)
        Usuario usuario1 = new Usuario("U1", "Ana", "ana@mail.com");

        // Usuario que solo quiere notificaciones por consola y solo WARNING y ERROR
        Usuario usuario2 = new Usuario("U2", "Pedro", "pedro@mail.com");
        PreferenciasNotificacion prefUsuario2 = new PreferenciasNotificacion(
                false, // No email
                true,  // Sí consola
                false, // No INFO
                true,  // Sí WARNING
                true   // Sí ERROR
        );
        usuario2.setPreferencias(prefUsuario2);

        // Usuario que solo quiere notificaciones por email y solo ERROR
        Usuario usuario3 = new Usuario("U3", "María", "maria@mail.com");
        PreferenciasNotificacion prefUsuario3 = new PreferenciasNotificacion(
                true,  // Sí email
                false, // No consola
                false, // No INFO
                false, // No WARNING
                true   // Sí ERROR
        );
        usuario3.setPreferencias(prefUsuario3);

        // Mostrar preferencias de cada usuario
        System.out.println("👤 Preferencias de Ana: " + usuario1.getPreferencias());
        System.out.println("👤 Preferencias de Pedro: " + usuario2.getPreferencias());
        System.out.println("👤 Preferencias de María: " + usuario3.getPreferencias());

        // Simular envío de notificaciones según preferencias
        System.out.println("\n📤 Simulando envío de notificaciones según preferencias:");

        // Crear recordatorios con diferentes niveles
        Recordatorio infoRecordatorio = new Recordatorio(usuario1, "Actualización", "Nueva versión disponible", Recordatorio.NivelUrgencia.INFO);
        Recordatorio warningRecordatorio = new Recordatorio(usuario2, "Vencimiento", "Tu préstamo vence pronto", Recordatorio.NivelUrgencia.WARNING);
        Recordatorio errorRecordatorio = new Recordatorio(usuario3, "Error", "Problema con tu cuenta", Recordatorio.NivelUrgencia.ERROR);

        // Mostrar mensajes formateados
        System.out.println("ℹ️ Mensaje INFO: " + infoRecordatorio.getMensajeFormateado());
        System.out.println("⚠️ Mensaje WARNING: " + warningRecordatorio.getMensajeFormateado());
        System.out.println("🚨 Mensaje ERROR: " + errorRecordatorio.getMensajeFormateado());

        // Verificar si los usuarios recibirían las notificaciones según sus preferencias
        System.out.println("\n🔍 Verificando recepción según preferencias:");

        verificarRecepcion(usuario1, infoRecordatorio);
        verificarRecepcion(usuario1, warningRecordatorio);
        verificarRecepcion(usuario1, errorRecordatorio);

        verificarRecepcion(usuario2, infoRecordatorio);
        verificarRecepcion(usuario2, warningRecordatorio);
        verificarRecepcion(usuario2, errorRecordatorio);

        verificarRecepcion(usuario3, infoRecordatorio);
        verificarRecepcion(usuario3, warningRecordatorio);
        verificarRecepcion(usuario3, errorRecordatorio);

        // Cerrar servicios
        servicioEmail.cerrar();

        System.out.println("\n✅ TestNotificacion ejecutado con éxito.");
    }

    /**
     * Método auxiliar para verificar si un usuario recibiría un recordatorio según sus preferencias.
     */
    private static void verificarRecepcion(Usuario usuario, Recordatorio recordatorio) {
        boolean debeRecibir = usuario.getPreferencias().debeRecibirPorNivel(recordatorio.getNivel());
        String canalEmail = usuario.getPreferencias().isRecibirPorEmail() ? "email" : "no email";
        String canalConsola = usuario.getPreferencias().isRecibirPorConsola() ? "consola" : "no consola";

        System.out.println(usuario.getNombre() + " - " + recordatorio.getNivel() + ": " + 
                          (debeRecibir ? "SÍ recibiría" : "NO recibiría") + 
                          " por " + canalEmail + " y " + canalConsola);
    }
}
