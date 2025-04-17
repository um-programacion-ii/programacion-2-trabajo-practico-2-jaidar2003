package test;

import modelo.CategoriaRecurso;
import modelo.Usuario;
import recurso.Libro;
import recurso.Revista;
import servicio.logica.GestorReservas;

public class TestReservas {
    public static void main(String[] args) throws InterruptedException {
        GestorReservas gestor = new GestorReservas();

        Usuario usuario1 = new Usuario("U1", "Ana", "ana@mail.com");
        Usuario usuario2 = new Usuario("U2", "Luis", "luis@mail.com");

        Libro libro = new Libro("L10", "1984", "George Orwell", "1234567890", CategoriaRecurso.FICCION);
        Revista revista = new Revista("R5", "National Geo", 310, CategoriaRecurso.CIENCIA);

        gestor.agregarReserva(usuario1, libro);
        gestor.agregarReserva(usuario2, revista);

        gestor.mostrarReservasPendientes();

        System.out.println("\n🔁 Devolviendo recurso y liberando reservas...");
        gestor.liberarRecurso(libro);
        gestor.liberarRecurso(revista);

        gestor.mostrarReservasPendientes();
    }
}
