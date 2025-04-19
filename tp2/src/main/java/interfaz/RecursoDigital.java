// interfazRecursoDigital.java
package interfaz;

import modelo.CategoriaRecurso;
import modelo.EstadoRecurso;

public interface RecursoDigital {
    String getIdentificador();
    String getTitulo();
    EstadoRecurso getEstado();
    void actualizarEstado(EstadoRecurso estado);

    CategoriaRecurso getCategoria();
}
