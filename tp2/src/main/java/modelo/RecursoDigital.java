package modelo;

import modelo.EstadoRecurso; // ⬅️ ¡IMPORTANTE!

public interface RecursoDigital {
    String getIdentificador();
    EstadoRecurso getEstado();
    void actualizarEstado(EstadoRecurso nuevoEstado);
}
