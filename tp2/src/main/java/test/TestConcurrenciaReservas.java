package test;

import modelo.CategoriaRecurso;
import modelo.Usuario;
import recurso.Libro;
import servicio.logica.GestorReservas;

public class TestConcurrenciaReservas {
    public static void main(String[] args) {
        System.out.println("🧪 Test de concurrencia en reservas\n");

        GestorReservas gestorReservas = new GestorReservas();
        Libro libro = new Libro("L100", "El Señor de los Anillos", "J.R.R. Tolkien", "123456789", CategoriaRecurso.FICCION);

        // Crear varios usuarios
        Usuario[] usuarios = {
                new Usuario("U1", "Juan", "juan@mail.com"),
                new Usuario("U2", "Ana", "ana@mail.com"),
                new Usuario("U3", "Luis", "luis@mail.com"),
                new Usuario("U4", "Sofía", "sofia@mail.com"),
                new Usuario("U5", "Pedro", "pedro@mail.com")
        };

        // Lanzar múltiples hilos que intentan reservar el mismo recurso
        for (Usuario usuario : usuarios) {
            Thread hilo = new Thread(() -> {
                try {
                    gestorReservas.agregarReserva(usuario, libro);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            hilo.start();
        }

        // Esperar un segundo para que todos terminen
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Corrección del segundo hilo
        Usuario usuarioAdicional = new Usuario("U6", "Carlos", "carlos@mail.com");
        Thread hilo = new Thread(() -> {
            try {
                gestorReservas.agregarReserva(usuarioAdicional, libro);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        hilo.start();

        // Esperar que termine el último hilo
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gestorReservas.mostrarReservas();
    }
}