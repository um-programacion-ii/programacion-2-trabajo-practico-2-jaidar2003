package test;

import modelo.CategoriaRecurso;
import modelo.Prestamo;
import modelo.Usuario;
import recurso.Audiolibro;
import recurso.Libro;
import recurso.Revista;
import servicio.logica.GestorPrestamos;
import servicio.logica.GestorRecursos;
import servicio.notificacion.NotificacionConsola;
import servicio.notificacion.ServicioNotificaciones;
import servicio.notificacion.ServicioNotificacionesEmail;
import excepciones.RecursoNoDisponibleException;

import java.util.List;

/**
 * Test para el sistema de préstamos y devoluciones que prueba diferentes escenarios,
 * tipos de recursos, y manejo de errores.
 */
public class TestPrestamoDevolucion {
    public static void main(String[] args) {
        System.out.println("\n🔍 TEST: Sistema de Préstamos y Devoluciones");
        System.out.println("=========================================");

        // Crear gestores y servicios
        ServicioNotificacionesEmail servicioEmail = new ServicioNotificacionesEmail();
        GestorRecursos gestorRecursos = new GestorRecursos(servicioEmail);
        GestorPrestamos gestorPrestamos = new GestorPrestamos();

        // Crear usuarios para pruebas
        Usuario ana = new Usuario("U1", "Ana", "ana@mail.com");
        Usuario pedro = new Usuario("U2", "Pedro", "pedro@mail.com");
        Usuario maria = new Usuario("U3", "María", "maria@mail.com");

        // Crear diferentes tipos de recursos
        System.out.println("\n📚 Creando recursos para pruebas...");
        Libro libro1 = new Libro("L1", "1984", "George Orwell", "978-0451524935", CategoriaRecurso.LITERATURA);
        Libro libro2 = new Libro("L2", "Cien Años de Soledad", "Gabriel García Márquez", "978-0307474728", CategoriaRecurso.LITERATURA);
        Revista revista1 = new Revista("R1", "National Geographic", 202, CategoriaRecurso.CIENCIA);
        Audiolibro audio1 = new Audiolibro("A1", "El Principito", "Antonio de Valdivia", CategoriaRecurso.FICCION);

        // Registrar recursos
        gestorRecursos.registrarRecurso(libro1);
        gestorRecursos.registrarRecurso(libro2);
        gestorRecursos.registrarRecurso(revista1);
        gestorRecursos.registrarRecurso(audio1);

        // Prueba 1: Préstamo y devolución básicos
        System.out.println("\n🧪 Prueba 1: Préstamo y devolución básicos");
        try {
            // Prestar libro a Ana
            System.out.println("📤 Prestando '1984' a Ana...");
            gestorPrestamos.registrarPrestamo(ana, libro1);

            // Verificar préstamos activos
            System.out.println("📋 Préstamos activos después de prestar:");
            gestorPrestamos.listarPrestamos();

            // Devolver libro
            System.out.println("📥 Devolviendo '1984'...");
            gestorRecursos.devolver("L1");

            // Verificar préstamos activos después de devolución
            System.out.println("📋 Préstamos activos después de devolver:");
            gestorPrestamos.listarPrestamos();

        } catch (RecursoNoDisponibleException e) {
            System.out.println("❌ Error en Prueba 1: " + e.getMessage());
        }

        // Prueba 2: Préstamo, renovación y devolución
        System.out.println("\n🧪 Prueba 2: Préstamo, renovación y devolución");
        try {
            // Prestar revista a Pedro
            System.out.println("📤 Prestando 'National Geographic' a Pedro...");
            gestorPrestamos.registrarPrestamo(pedro, revista1);

            // Obtener lista de préstamos
            List<Prestamo> prestamos = gestorPrestamos.getPrestamos();

            // Verificar préstamos antes de renovar
            System.out.println("📋 Préstamo antes de renovar:");
            for (Prestamo p : prestamos) {
                if (p.getRecurso().equals(revista1)) {
                    System.out.println(p + " - Fecha fin: " + p.getFechaFin());
                }
            }

            // Renovar préstamo
            System.out.println("🔄 Renovando préstamo por 14 días...");
            for (Prestamo p : prestamos) {
                if (p.getRecurso().equals(revista1)) {
                    gestorPrestamos.renovarPrestamo(p, 14);
                }
            }

            // Verificar préstamos después de renovar
            System.out.println("📋 Préstamo después de renovar:");
            for (Prestamo p : prestamos) {
                if (p.getRecurso().equals(revista1)) {
                    System.out.println(p + " - Fecha fin: " + p.getFechaFin());
                }
            }

            // Devolver revista
            System.out.println("📥 Devolviendo 'National Geographic'...");
            gestorRecursos.devolver("R1");

        } catch (RecursoNoDisponibleException e) {
            System.out.println("❌ Error en Prueba 2: " + e.getMessage());
        }

        // Prueba 3: Préstamos múltiples
        System.out.println("\n🧪 Prueba 3: Préstamos múltiples");
        try {
            // Prestar varios recursos a María
            System.out.println("📤 Prestando 'Cien Años de Soledad' a María...");
            gestorPrestamos.registrarPrestamo(maria, libro2);

            System.out.println("📤 Prestando 'El Principito' a María...");
            gestorPrestamos.registrarPrestamo(maria, audio1);

            // Verificar préstamos activos
            System.out.println("📋 Préstamos activos de María:");
            List<Prestamo> prestamosMaria = gestorPrestamos.getPrestamos().stream()
                    .filter(p -> p.getUsuario().equals(maria))
                    .toList();

            for (Prestamo p : prestamosMaria) {
                System.out.println(p);
            }

            // Devolver recursos
            System.out.println("📥 Devolviendo 'Cien Años de Soledad'...");
            gestorRecursos.devolver("L2");

            System.out.println("📥 Devolviendo 'El Principito'...");
            gestorRecursos.devolver("A1");

        } catch (RecursoNoDisponibleException e) {
            System.out.println("❌ Error en Prueba 3: " + e.getMessage());
        }

        // Prueba 4: Manejo de errores
        System.out.println("\n🧪 Prueba 4: Manejo de errores");

        // Caso 1: Intentar prestar un recurso que no existe
        try {
            System.out.println("❓ Intentando prestar un recurso inexistente...");
            gestorPrestamos.registrarPrestamo(ana, null);
        } catch (Exception e) {
            System.out.println("✅ Error capturado correctamente: " + e.getMessage());
        }

        // Caso 2: Intentar prestar un recurso ya prestado
        try {
            System.out.println("❓ Intentando prestar un recurso ya prestado...");
            // Primero prestamos el libro a Ana
            gestorPrestamos.registrarPrestamo(ana, libro1);
            // Luego intentamos prestarlo a Pedro
            gestorPrestamos.registrarPrestamo(pedro, libro1);
        } catch (RecursoNoDisponibleException e) {
            System.out.println("✅ Error capturado correctamente: " + e.getMessage());
        }

        // Caso 3: Intentar devolver un recurso que no está prestado
        try {
            System.out.println("❓ Intentando devolver un recurso que no está prestado...");
            // Primero devolvemos el libro
            gestorRecursos.devolver("L1");
            // Luego intentamos devolverlo de nuevo
            gestorRecursos.devolver("L1");
        } catch (RecursoNoDisponibleException e) {
            System.out.println("✅ Error capturado correctamente: " + e.getMessage());
        }

        // Cerrar servicios
        servicioEmail.cerrar();

        System.out.println("\n✅ TestPrestamoDevolucion ejecutado con éxito.");
    }
}
