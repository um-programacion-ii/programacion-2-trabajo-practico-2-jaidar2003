package test;

import modelo.CategoriaRecurso;
import recurso.Libro;
import servicio.logica.GestorRecursos;
import servicio.notificacion.ServicioNotificacionesEmail;
import excepciones.RecursoNoDisponibleException;

public class TestPrestamoDevolucion {
    public static void main(String[] args) {
        GestorRecursos gestor = new GestorRecursos(new ServicioNotificacionesEmail());

        Libro libro = new Libro("L2", "Cien Años de Soledad", "Gabriel García Márquez", "ISBN456", CategoriaRecurso.ARTE);
        gestor.registrarRecurso(libro);

        try {
            gestor.prestar("L2");
            gestor.devolver("L2");
        } catch (RecursoNoDisponibleException e) {
            System.out.println("❌ Error al prestar o devolver: " + e.getMessage());
        }
    }
}
