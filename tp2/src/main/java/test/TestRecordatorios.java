package test;

import modelo.PreferenciasNotificacion;
import modelo.Recordatorio;
import modelo.Usuario;
import servicio.logica.GestorRecordatorios;

public class TestRecordatorios {
    public static void main(String[] args) {
        System.out.println("\n🔔 TEST: Sistema de Recordatorios Automáticos");
        System.out.println("==============================================");

        // Crear sistema de recordatorios
        GestorRecordatorios sistema = new GestorRecordatorios();

        // Crear usuarios con diferentes preferencias
        Usuario usuario1 = new Usuario("U1", "Ana", "ana@mail.com");
        Usuario usuario2 = new Usuario("U2", "Luis", "luis@mail.com");
        Usuario usuario3 = new Usuario("U3", "Carlos", "carlos@mail.com");

        // Configurar preferencias personalizadas para usuario2 (solo consola, sin INFO)
        PreferenciasNotificacion prefUsuario2 = new PreferenciasNotificacion(
                false, // No email
                true,  // Sí consola
                false, // No INFO
                true,  // Sí WARNING
                true   // Sí ERROR
        );
        usuario2.setPreferencias(prefUsuario2);

        // Configurar preferencias personalizadas para usuario3 (solo email, solo ERROR)
        PreferenciasNotificacion prefUsuario3 = new PreferenciasNotificacion(
                true,  // Sí email
                false, // No consola
                false, // No INFO
                false, // No WARNING
                true   // Sí ERROR
        );
        usuario3.setPreferencias(prefUsuario3);

        System.out.println("\n📝 Creando recordatorios con diferentes niveles de urgencia...");
        
        // Crear recordatorios para usuario1 (todas las preferencias activadas por defecto)
        sistema.crearRecordatorio(
                usuario1,
                "Actualización del sistema",
                "Se ha actualizado el sistema a la versión 2.0",
                Recordatorio.NivelUrgencia.INFO
        );
        
        sistema.crearRecordatorio(
                usuario1,
                "Mantenimiento programado",
                "El sistema estará en mantenimiento mañana de 2 a 4 PM",
                Recordatorio.NivelUrgencia.WARNING
        );
        
        sistema.crearRecordatorio(
                usuario1,
                "Error crítico",
                "Se ha detectado un error en la base de datos",
                Recordatorio.NivelUrgencia.ERROR
        );

        // Crear recordatorios para usuario2 (no debería recibir INFO)
        sistema.crearRecordatorio(
                usuario2,
                "Nueva funcionalidad",
                "Se ha añadido una nueva funcionalidad al sistema",
                Recordatorio.NivelUrgencia.INFO
        );
        
        sistema.crearRecordatorio(
                usuario2,
                "Contraseña por vencer",
                "Su contraseña vencerá en 3 días",
                Recordatorio.NivelUrgencia.WARNING
        );

        // Crear recordatorios para usuario3 (solo debería recibir ERROR por email)
        sistema.crearRecordatorio(
                usuario3,
                "Actualización de términos",
                "Se han actualizado los términos de servicio",
                Recordatorio.NivelUrgencia.INFO
        );
        
        sistema.crearRecordatorio(
                usuario3,
                "Fallo de seguridad",
                "Se ha detectado un intento de acceso no autorizado",
                Recordatorio.NivelUrgencia.ERROR
        );

        // Mostrar historial completo de recordatorios
        System.out.println("\n📋 Historial completo de recordatorios:");
        sistema.mostrarHistorialRecordatorios();

        // Mostrar recordatorios por usuario
        System.out.println("\n👤 Recordatorios por usuario:");
        sistema.mostrarRecordatoriosUsuario(usuario1);
        sistema.mostrarRecordatoriosUsuario(usuario2);
        sistema.mostrarRecordatoriosUsuario(usuario3);

        // Marcar algunos recordatorios como leídos
        System.out.println("\n✅ Marcando algunos recordatorios como leídos...");
        sistema.getRecordatoriosUsuario(usuario1).get(0).marcarComoLeido();
        if (!sistema.getRecordatoriosUsuario(usuario2).isEmpty()) {
            sistema.getRecordatoriosUsuario(usuario2).get(0).marcarComoLeido();
        }

        // Mostrar recordatorios no leídos
        System.out.println("\n📬 Recordatorios no leídos por usuario:");
        System.out.println("Usuario 1: " + sistema.getRecordatoriosNoLeidos(usuario1).size() + " recordatorios no leídos");
        System.out.println("Usuario 2: " + sistema.getRecordatoriosNoLeidos(usuario2).size() + " recordatorios no leídos");
        System.out.println("Usuario 3: " + sistema.getRecordatoriosNoLeidos(usuario3).size() + " recordatorios no leídos");

        // Cerrar el sistema de recordatorios
        sistema.cerrar();

        System.out.println("\n✅ Test de Sistema de Recordatorios completado.");
    }
}