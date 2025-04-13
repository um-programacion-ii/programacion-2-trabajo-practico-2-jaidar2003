package test;


import recurso.Libro;
import servicio.logica.GestorRecursos;

public class TestPrestamoDevolucion {
    public static void main(String[] args) {
        GestorRecursos gestor = new GestorRecursos();
        Libro libro = new Libro("L2", "Cien Años de Soledad", "Gabriel García Márquez", "ISBN456");

        gestor.registrarRecurso(libro);
        gestor.prestar("L2");
        gestor.devolver("L2");
    }
}
