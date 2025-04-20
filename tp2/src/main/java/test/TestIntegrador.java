package test;

import modelo.CategoriaRecurso;
import modelo.Prestamo;
import modelo.Reserva;
import modelo.Usuario;
import recurso.Audiolibro;
import recurso.Libro;
import recurso.Revista;
import servicio.logica.GestorPrestamos;
import servicio.logica.GestorRecursos;
import servicio.logica.GestorReservas;
import servicio.logica.GestorUsuarios;
import servicio.notificacion.NotificacionConsola;
import servicio.notificacion.ServicioNotificacionesEmail;
import servicio.notificacion.ServicioNotificacionesEmailAdapter;
import excepciones.RecursoNoDisponibleException;

import java.util.List;

/**
 * Test integrador que prueba la interacción entre múltiples componentes del sistema.
 * Verifica diferentes escenarios y casos de uso.
 */
public class TestIntegrador {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n🔍 TEST INTEGRADOR");
        System.out.println("=================");

        // Crear gestores
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        GestorPrestamos gestorPrestamos = new GestorPrestamos();
        GestorReservas gestorReservas = new GestorReservas(gestorPrestamos);
        ServicioNotificacionesEmail servicioEmail = new ServicioNotificacionesEmail();
        GestorRecursos gestorRecursos = new GestorRecursos(servicioEmail);

        // Configurar notificaciones
        gestorReservas.setServicioNotificaciones(new ServicioNotificacionesEmailAdapter(servicioEmail));

        // Crear usuarios
        System.out.println("\n👥 Registrando usuarios...");
        Usuario ana = new Usuario("U1", "Ana", "ana@mail.com");
        Usuario pedro = new Usuario("U2", "Pedro", "pedro@mail.com");
        Usuario maria = new Usuario("U3", "María", "maria@mail.com");

        gestorUsuarios.registrarUsuario(ana);
        gestorUsuarios.registrarUsuario(pedro);
        gestorUsuarios.registrarUsuario(maria);

        // Listar usuarios
        gestorUsuarios.listarUsuarios();

        // Crear recursos
        System.out.println("\n📚 Registrando recursos...");
        Libro libro1 = new Libro("L1", "El Principito", "Antoine de Saint-Exupéry", "978-0156012195", CategoriaRecurso.FICCION);
        Libro libro2 = new Libro("L2", "Cien Años de Soledad", "Gabriel García Márquez", "978-0307474728", CategoriaRecurso.LITERATURA);
        Revista revista1 = new Revista("R1", "National Geographic", 202, CategoriaRecurso.CIENCIA);
        Audiolibro audio1 = new Audiolibro("A1", "1984", "Carlos Pérez", CategoriaRecurso.HISTORIA);

        gestorRecursos.registrarRecurso(libro1);
        gestorRecursos.registrarRecurso(libro2);
        gestorRecursos.registrarRecurso(revista1);
        gestorRecursos.registrarRecurso(audio1);

        // Listar recursos
        gestorRecursos.listarRecursos();

        // Escenario 1: Préstamo y devolución exitosos
        System.out.println("\n🧪 Escenario 1: Préstamo y devolución exitosos");
        try {
            System.out.println("📤 Prestando libro a Ana...");
            gestorPrestamos.registrarPrestamo(ana, libro1);

            // Verificar préstamos
            System.out.println("📋 Lista de préstamos después de prestar:");
            gestorPrestamos.listarPrestamos();

            System.out.println("📥 Devolviendo libro...");
            gestorRecursos.devolver("L1");

            // Verificar préstamos después de devolución
            System.out.println("📋 Lista de préstamos después de devolver:");
            gestorPrestamos.listarPrestamos();
        } catch (RecursoNoDisponibleException e) {
            System.out.println("❌ Error en escenario 1: " + e.getMessage());
        }

        // Escenario 2: Préstamo, renovación y devolución
        System.out.println("\n🧪 Escenario 2: Préstamo, renovación y devolución");
        try {
            System.out.println("📤 Prestando libro a Pedro...");
            gestorPrestamos.registrarPrestamo(pedro, libro2);

            // Renovar préstamo
            System.out.println("🔄 Renovando préstamo...");
            List<Prestamo> prestamos = gestorPrestamos.getPrestamos();
            if (!prestamos.isEmpty()) {
                gestorPrestamos.renovarPrestamo(prestamos.get(0), 14);
            }

            // Verificar préstamos después de renovación
            System.out.println("📋 Lista de préstamos después de renovar:");
            gestorPrestamos.listarPrestamos();

            // Devolver
            System.out.println("📥 Devolviendo libro...");
            gestorRecursos.devolver("L2");
        } catch (RecursoNoDisponibleException e) {
            System.out.println("❌ Error en escenario 2: " + e.getMessage());
        }

        // Escenario 3: Reserva y liberación
        System.out.println("\n🧪 Escenario 3: Reserva y liberación");
        try {
            // Prestar recurso a un usuario
            System.out.println("📤 Prestando revista a Ana...");
            gestorPrestamos.registrarPrestamo(ana, revista1);

            // Reservar el mismo recurso para otro usuario
            System.out.println("🔖 Reservando revista para María...");
            gestorReservas.agregarReserva(maria, revista1);

            // Mostrar reservas
            System.out.println("📋 Reservas pendientes:");
            gestorReservas.mostrarReservasPendientes();

            // Liberar recurso (devolver)
            System.out.println("📥 Devolviendo revista (liberando reserva)...");
            gestorRecursos.devolver("R1");

            // Procesar alertas de disponibilidad
            System.out.println("🔔 Procesando alertas de disponibilidad:");
            gestorReservas.procesarAlertasDisponibilidad(true);

            // Mostrar recursos disponibles para el usuario
            System.out.println("📚 Recursos disponibles para María:");
            gestorReservas.mostrarRecursosDisponibles(maria);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("❌ Error en escenario 3: " + e.getMessage());
        }

        // Escenario 4: Acceso a recursos digitales
        System.out.println("\n🧪 Escenario 4: Acceso a recursos digitales");
        try {
            // Acceder en línea
            System.out.println("🌐 Accediendo al audiolibro en línea...");
            gestorRecursos.accederOnline("A1");

            // Descargar
            System.out.println("💾 Descargando audiolibro...");
            gestorRecursos.descargar("A1");
        } catch (RecursoNoDisponibleException e) {
            System.out.println("❌ Error en escenario 4: " + e.getMessage());
        }

        // Escenario 5: Manejo de errores
        System.out.println("\n🧪 Escenario 5: Manejo de errores");
        try {
            // Intentar prestar un recurso inexistente
            System.out.println("❓ Intentando prestar un recurso inexistente...");
            gestorRecursos.prestar("NOEXISTE");
        } catch (RecursoNoDisponibleException e) {
            System.out.println("✅ Error capturado correctamente: " + e.getMessage());
        }

        try {
            // Intentar prestar un recurso ya prestado
            System.out.println("❓ Intentando prestar un recurso ya prestado...");
            gestorPrestamos.registrarPrestamo(ana, libro1);
            gestorPrestamos.registrarPrestamo(pedro, libro1);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("✅ Error capturado correctamente: " + e.getMessage());
        }

        // Cerrar servicios
        servicioEmail.cerrar();

        System.out.println("\n✅ TestIntegrador ejecutado con éxito.");
    }
}
