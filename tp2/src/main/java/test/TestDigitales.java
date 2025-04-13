
package test;
import recurso.Audiolibro;
import servicio.logica.GestorRecursos;

public class TestDigitales {
    public static void main(String[] args) {
        GestorRecursos gestor = new GestorRecursos();
        Audiolibro a = new Audiolibro("A2", "Los Miserables", "NarradorX");

        gestor.registrarRecurso(a);
        gestor.accederOnline("A2");
        gestor.descargar("A2");
    }
}
