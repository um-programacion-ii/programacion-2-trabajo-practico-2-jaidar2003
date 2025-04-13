package debug;

import recurso.Audiolibro;
import recurso.Libro;

public class Main {
    public static void main(String[] args) {
        System.out.println("📚 Bienvenido al Sistema de Gestión de Biblioteca\n");

        // 📖 Crear un libro
        Libro libro = new Libro("L1", "El Quijote", "Miguel de Cervantes", "978-8491050293");
        System.out.println(libro);
        libro.prestar();
        System.out.println("¿Está prestado?: " + libro.estaPrestado());
        libro.devolver();
        System.out.println("¿Está prestado?: " + libro.estaPrestado());

        System.out.println("\n--------------------------\n");

        // 🔊 Crear un audiolibro
        Audiolibro audiolibro = new Audiolibro("A1", "1984", "Carlos Pérez");
        System.out.println(audiolibro);
        audiolibro.accederEnLinea();
        audiolibro.descargar();
        audiolibro.renovar();
    }
}
