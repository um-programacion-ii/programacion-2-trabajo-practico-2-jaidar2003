package test;

import modelo.CategoriaRecurso;
import recurso.Audiolibro;
import servicio.logica.GestorRecursos;
import servicio.notificacion.ServicioNotificacionesEmail;
import excepciones.RecursoNoDisponibleException;

public class TestDigitales {
    public static void main(String[] args) {
        GestorRecursos gestor = new GestorRecursos(new ServicioNotificacionesEmail());

        Audiolibro a = new Audiolibro("A2", "Los Miserables", "Luis Pérez", CategoriaRecurso.ARTE);
        gestor.registrarRecurso(a);

        try {
            gestor.accederOnline("A2");
            gestor.descargar("A2");
        } catch (RecursoNoDisponibleException e) {
            System.out.println("❌ Error al acceder o descargar recurso digital: " + e.getMessage());
        }

        System.out.println("✅ TestDigitales ejecutado.\n");
    }
}
