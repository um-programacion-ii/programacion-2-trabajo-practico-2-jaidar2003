package test;

import modelo.CategoriaRecurso;
import modelo.Usuario;
import recurso.Libro;
import recurso.Revista;
import servicio.logica.GestorPrestamos;
import servicio.logica.GestorReservas;
import servicio.notificacion.NotificacionConsola;
import servicio.notificacion.ServicioNotificacionesEmail;
import servicio.notificacion.ServicioNotificacionesEmailAdapter;

public class TestAlertasDisponibilidad {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n🔔 TEST: Alertas de Disponibilidad de Recursos");
        System.out.println("==============================================");

        // Crear gestores
        GestorPrestamos gestorPrestamos = new GestorPrestamos();
        GestorReservas gestorReservas = new GestorReservas(gestorPrestamos);

        // Configurar notificaciones por consola (por defecto)
        gestorReservas.setServicioNotificaciones(new NotificacionConsola());

        // Crear usuarios
        Usuario usuario1 = new Usuario("U1", "Ana", "ana@mail.com");
        Usuario usuario2 = new Usuario("U2", "Luis", "luis@mail.com");

        // Crear recursos
        Libro libro = new Libro("L10", "1984", "George Orwell", "1234567890", CategoriaRecurso.FICCION);
        Revista revista = new Revista("R5", "National Geo", 310, CategoriaRecurso.CIENCIA);

        // Agregar reservas
        System.out.println("\n📝 Agregando reservas...");
        gestorReservas.agregarReserva(usuario1, libro);
        gestorReservas.agregarReserva(usuario2, revista);
        gestorReservas.agregarReserva(usuario1, revista);

        // Mostrar reservas pendientes
        System.out.println("\n📋 Reservas pendientes:");
        gestorReservas.mostrarReservasPendientes();

        // Liberar recursos (esto generará alertas de disponibilidad)
        System.out.println("\n🔄 Liberando recursos...");
        gestorReservas.liberarRecurso(libro);
        gestorReservas.liberarRecurso(revista);

        // Mostrar recursos disponibles para cada usuario
        System.out.println("\n📚 Recursos disponibles por usuario:");
        gestorReservas.mostrarRecursosDisponibles(usuario1);
        gestorReservas.mostrarRecursosDisponibles(usuario2);

        // Procesar alertas en modo test (sin interacción)
        System.out.println("\n🔔 Procesando alertas de disponibilidad (modo test):");
        gestorReservas.procesarAlertasDisponibilidad(true);

        // Cambiar a notificaciones por email
        System.out.println("\n📧 Cambiando a notificaciones por email...");
        ServicioNotificacionesEmail servicioEmail = new ServicioNotificacionesEmail();
        ServicioNotificacionesEmailAdapter adaptadorEmail = new ServicioNotificacionesEmailAdapter(servicioEmail);
        gestorReservas.setServicioNotificaciones(adaptadorEmail);

        // Crear nuevas reservas
        System.out.println("\n📝 Agregando nuevas reservas...");
        Libro otroLibro = new Libro("L11", "El Señor de los Anillos", "J.R.R. Tolkien", "9876543210", CategoriaRecurso.FICCION);
        gestorReservas.agregarReserva(usuario1, otroLibro);

        // Liberar recurso (generará alerta por email)
        System.out.println("\n🔄 Liberando recurso (notificación por email)...");
        gestorReservas.liberarRecurso(otroLibro);

        // Cerrar servicio de email
        servicioEmail.cerrar();

        System.out.println("\n✅ Test de Alertas de Disponibilidad completado.");
    }
}
