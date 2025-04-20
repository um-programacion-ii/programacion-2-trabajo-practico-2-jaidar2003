package test;

import modelo.CategoriaRecurso;
import modelo.Prestamo;
import modelo.Usuario;
import recurso.Libro;
import recurso.Revista;
import servicio.logica.GestorReportes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TestReportes {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n🔍 TEST: Sistema de Reportes");
        System.out.println("==========================");

        // 👤 Crear usuarios
        Usuario ana = new Usuario("U1", "Ana", "ana@mail.com");
        Usuario juan = new Usuario("U2", "Juan", "juan@mail.com");
        Usuario pedro = new Usuario("U3", "Pedro", "pedro@mail.com");

        // 📚 Crear recursos
        Libro libro1 = new Libro("L1", "1984", "Orwell", "ISBN1", CategoriaRecurso.FICCION);
        Libro libro2 = new Libro("L2", "Cien Años de Soledad", "García Márquez", "ISBN2", CategoriaRecurso.LITERATURA);
        Revista revista1 = new Revista("R1", "National Geographic", 100, CategoriaRecurso.CIENCIA);

        // 📦 Simular préstamos
        List<Prestamo> prestamos = new ArrayList<>();

        prestamos.add(new Prestamo(ana, libro1, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4)));
        prestamos.add(new Prestamo(ana, libro2, LocalDate.now().minusDays(8), LocalDate.now().plusDays(6)));
        prestamos.add(new Prestamo(juan, libro1, LocalDate.now().minusDays(5), LocalDate.now().plusDays(9)));
        prestamos.add(new Prestamo(pedro, libro1, LocalDate.now().minusDays(3), LocalDate.now().plusDays(11)));
        prestamos.add(new Prestamo(pedro, revista1, LocalDate.now().minusDays(2), LocalDate.now().plusDays(12)));
        prestamos.add(new Prestamo(pedro, libro2, LocalDate.now().minusDays(1), LocalDate.now().plusDays(13)));

        // 📊 Crear gestor de reportes
        GestorReportes gestor = new GestorReportes(prestamos);

        // Parte 1: Prueba de reportes síncronos
        System.out.println("\n🔄 Prueba de reportes síncronos:");
        gestor.mostrarTodosLosReportes();

        // Parte 2: Prueba de reportes asíncronos individuales
        System.out.println("\n🔄 Prueba de reportes asíncronos individuales:");

        // Generar reporte de recursos más prestados de forma asíncrona
        System.out.println("\n📊 Generando reporte de recursos más prestados...");
        Future<?> futuroRecursos = gestor.generarReporteRecursosMasPrestadosAsync("recursos-test");

        // Esperar a que termine el reporte
        while (!futuroRecursos.isDone()) {
            System.out.println("⏳ Esperando que termine el reporte de recursos...");
            Thread.sleep(500);
        }

        // Mostrar resultado
        System.out.println("\n📋 Resultado del reporte de recursos:");
        System.out.println(gestor.obtenerResultadoReporte("recursos-test"));

        // Parte 3: Prueba de múltiples reportes concurrentes
        System.out.println("\n🔄 Prueba de múltiples reportes concurrentes:");

        // Generar todos los reportes de forma asíncrona
        Map<String, Future<?>> reportes = gestor.generarTodosLosReportesAsync();

        // Monitorear el progreso de los reportes
        boolean todosTerminados = false;
        while (!todosTerminados) {
            gestor.mostrarEstadoReportes();

            // Verificar si todos los reportes han terminado
            todosTerminados = true;
            for (Future<?> futuro : reportes.values()) {
                if (!futuro.isDone()) {
                    todosTerminados = false;
                    break;
                }
            }

            if (!todosTerminados) {
                Thread.sleep(1000);
            }
        }

        // Mostrar resultados finales
        System.out.println("\n📋 Resultados finales de todos los reportes:");
        System.out.println(gestor.obtenerResultadoReporte("recursos"));
        System.out.println(gestor.obtenerResultadoReporte("usuarios"));
        System.out.println(gestor.obtenerResultadoReporte("categorias"));

        // Cerrar el gestor de reportes
        gestor.cerrar();

        System.out.println("\n✅ TestReportes ejecutado.");
    }
}
