package test;

import interfaz.ServicioNotificaciones;
import recurso.*;
import servicio.logica.GestorRecursos;
import servicio.notificacion.ServicioNotificacionesEmail;
import modelo.CategoriaRecurso;

public class TestNotificacionesEmail {
    public static void main(String[] args) {
        System.out.println("📧 Test con notificaciones por Email\n");

        // ✅ Crear implementación de notificaciones por email
        ServicioNotificaciones servicioEmail = new ServicioNotificacionesEmail();

        // ✅ Inyectar en el gestor de recursos
        GestorRecursos gestor = new GestorRecursos(servicioEmail);

        // ✅ Registrar recursos
        gestor.registrarRecurso(new Libro("L2", "Rayuela", "Julio Cortázar", "978-8437603794", CategoriaRecurso.LITERATURA));
        gestor.registrarRecurso(new Audiolibro("A2", "El Hobbit", "Laura Sánchez", CategoriaRecurso.FICCION));

        // ✅ Prestar, renovar y devolver
        gestor.prestar("L2");
        gestor.renovar("L2");
        gestor.devolver("L2");

        // ✅ Acceso online y descarga
        gestor.accederOnline("A2");
        gestor.descargar("A2");

        System.out.println("\n✅ TestNotificacionesEmail ejecutado correctamente.");
    }
}
