package test;

import recurso.Libro;
import servicio.logica.GestorRecursos;
import servicio.notificacion.ServicioNotificacionesEmail;
// o, si usás SMS:
// import servicio.notificacion.ServicioNotificacionesSMS;


public class TestPrestamoDevolucion {
    public static void main(String[] args) {
        //    GestorRecursos gestor = new GestorRecursos();
        GestorRecursos gestor = new GestorRecursos(new ServicioNotificacionesEmail());
    // o, si querés probar con SMS:
    // GestorRecursos gestor = new GestorRecursos(new ServicioNotificacionesSMS());

        Libro libro = new Libro("L2", "Cien Años de Soledad", "Gabriel García Márquez", "ISBN456");

        gestor.registrarRecurso(libro);
        gestor.prestar("L2");
        gestor.devolver("L2");
    }
}
