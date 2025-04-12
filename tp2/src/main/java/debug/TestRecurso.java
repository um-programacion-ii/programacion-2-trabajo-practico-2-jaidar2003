package debug;

import interfaz.Prestable;
import interfaz.Renovable;
import recurso.Libro;
import recurso.Revista;

public class TestRecurso {
    public static void main(String[] args) {
        // Crear un libro y una revista
        Libro libro = new Libro("L1", "El Principito", "Antoine de Saint-Exupéry", "978-0156012195");
        Revista revista = new Revista("R1", "National Geographic", 202);

        // Mostrar estado inicial
        System.out.println(libro);
        System.out.println("¿Está prestado el libro? " + libro.estaPrestado());

        System.out.println(revista);
        System.out.println("¿Está prestada la revista? " + revista.estaPrestado());

        // Prestar ambos recursos
        libro.prestar();
        revista.prestar();

        System.out.println("\n📦 Después del préstamo:");
        System.out.println(libro);
        System.out.println("¿Está prestado el libro? " + libro.estaPrestado());

        System.out.println(revista);
        System.out.println("¿Está prestada la revista? " + revista.estaPrestado());

        // Renovar el libro
        libro.renovar();

        // Devolver ambos
        libro.devolver();
        revista.devolver();

        System.out.println("\n📦 Después de la devolución:");
        System.out.println(libro);
        System.out.println("¿Está prestado el libro? " + libro.estaPrestado());

        System.out.println(revista);
        System.out.println("¿Está prestada la revista? " + revista.estaPrestado());
    }
}
