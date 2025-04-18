package test;

import modelo.CategoriaRecurso;
import recurso.Libro;
import servicio.notificacion.ServicioNotificacionesEmail;
import servicio.logica.GestorRecursos;

public class TestConcurrenciaPrestamos {

    public static void main(String[] args) {
        System.out.println("🧪 Test de concurrencia en préstamos\n");

        // Crear recurso compartido
        Libro libro = new Libro("L100", "Cálculo Avanzado", "James Stewart", "978-0538497817", CategoriaRecurso.EDUCACION);

        // Crear gestor e inyectar el recurso
        GestorRecursos gestor = new GestorRecursos(new ServicioNotificacionesEmail());
        gestor.registrarRecurso(libro);

        // Crear 5 hilos que intentan prestar el mismo recurso
        for (int i = 1; i <= 5; i++) {
            int usuario = i;
            Thread hilo = new Thread(() -> {
                System.out.println("🧵 Hilo " + usuario + " intentando prestar el recurso...");
                try {
                    gestor.prestar("L100");
                } catch (Exception e) {
                    System.out.println("❌ Hilo " + usuario + " - Error: " + e.getMessage());
                }
            });
            hilo.start();
        }
    }
}
