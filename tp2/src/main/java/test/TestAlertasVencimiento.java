package test;

import modelo.CategoriaRecurso;
import modelo.Usuario;
import modelo.Prestamo;
import recurso.Libro;
import servicio.logica.GestorPrestamos;

import java.time.LocalDate;

public class TestAlertasVencimiento {
    public static void main(String[] args) {
        System.out.println("🔔 Test de alertas por vencimiento\n");

        GestorPrestamos gestor = new GestorPrestamos();

        Usuario usuario = new Usuario("U1", "Juan", "juan@mail.com");
        Libro libro1 = new Libro("L1", "1984", "George Orwell", "1234567890", CategoriaRecurso.FICCION);
        Libro libro2 = new Libro("L2", "Rayuela", "Julio Cortázar", "4567891230", CategoriaRecurso.LITERATURA);

        // Simular préstamos con distintas fechas
        Prestamo prestamo1 = new Prestamo(usuario, libro1,
                LocalDate.now().minusDays(6), LocalDate.now()); // vence hoy
        Prestamo prestamo2 = new Prestamo(usuario, libro2,
                LocalDate.now().minusDays(6), LocalDate.now().plusDays(1)); // vence mañana

        gestor.getPrestamos().add(prestamo1);
        gestor.getPrestamos().add(prestamo2);

        // Verificar alertas
        gestor.verificarVencimientos();

        System.out.println("\n✅ TestAlertasVencimiento ejecutado.");
    }
}
