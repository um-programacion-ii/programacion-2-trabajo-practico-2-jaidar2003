package test;

import recurso.Audiolibro;
import servicio.logica.GestorRecursos;
import servicio.notificacion.ServicioNotificacionesEmail;

public class TestDigitales {
    public static void main(String[] args) {
        GestorRecursos gestor = new GestorRecursos(new ServicioNotificacionesEmail());

        Audiolibro a = new Audiolibro("A2", "Los Miserables", "Luis Pérez");
        gestor.registrarRecurso(a);

        gestor.accederOnline("A2");
        gestor.descargar("A2");

        System.out.println("✅ TestDigitales ejecutado.\n");
    }
}
