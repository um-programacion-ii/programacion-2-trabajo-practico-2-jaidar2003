package servicio.visualizacion;

import recurso.RecursoBase;

import java.util.List;

public class VisualizadorRecursos {
    public static void mostrarRecursos(List<RecursoBase> recursos) {
        System.out.println("📚 Lista de Recursos Disponibles:\n");
        for (RecursoBase recurso : recursos) {
            System.out.println(recurso);
        }
    }
}
