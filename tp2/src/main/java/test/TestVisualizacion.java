package test;

import modelo.CategoriaRecurso;
import recurso.Libro;
import recurso.Revista;
import recurso.Audiolibro;
import recurso.RecursoBase;
import servicio.visualizacion.VisualizadorRecursos;

import java.util.ArrayList;
import java.util.List;

public class TestVisualizacion {
    public static void main(String[] args) {
        List<RecursoBase> recursos = new ArrayList<>();
        recursos.add(new Libro("L1", "El Principito", "Antoine", "978-0156012195", CategoriaRecurso.ARTE));
        recursos.add(new Revista("R1", "National Geographic", 202, CategoriaRecurso.ARTE));
        recursos.add(new Audiolibro("A1", "1984", "Carlos Pérez", CategoriaRecurso.ARTE));

        VisualizadorRecursos.mostrarRecursos(recursos);
    }
}