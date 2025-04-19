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

public class TestReportes {
    public static void main(String[] args) {
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

        // 🧾 Mostrar todos los reportes
        gestor.mostrarTodosLosReportes();

        System.out.println("\n✅ TestReportes ejecutado.");
    }
}
