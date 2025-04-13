package interfaz;

import modelo.EstadoRecurso;

/**
 * Contrato principal para todos los recursos digitales.
 */
public interface interfazRecursoDigital {
    String getIdentificador();
    EstadoRecurso getEstado();
    void actualizarEstado(EstadoRecurso estado);
    String getTitulo();
}
