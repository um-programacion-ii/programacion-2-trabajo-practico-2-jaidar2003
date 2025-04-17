package test;

import modelo.Usuario;
import modelo.Prestamo;
import modelo.CategoriaRecurso;
import recurso.Libro;
import java.time.LocalDate;

public class TestPrestamo {
    public static void main(String[] args) {
        Usuario usuario = new Usuario("U1", "Juan Pérez", "juan@mail.com");
        Libro libro = new Libro("L1", "1984", "George Orwell", "1234567890", CategoriaRecurso.FICCION);

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusDays(14); // Préstamo de 2 semanas

        Prestamo prestamo = new Prestamo(usuario, libro, fechaInicio, fechaFin);

        System.out.println("📚 Préstamo creado:");
        System.out.println("🔖 Recurso: " + prestamo.getRecurso().getTitulo());
        System.out.println("🙋 Usuario: " + prestamo.getUsuario().getNombre());
        System.out.println("📅 Desde: " + prestamo.getFechaInicio());
        System.out.println("📅 Hasta: " + prestamo.getFechaFin());
        System.out.println("✅ ¿Vencido?: " + prestamo.estaVencido());
    }
}
