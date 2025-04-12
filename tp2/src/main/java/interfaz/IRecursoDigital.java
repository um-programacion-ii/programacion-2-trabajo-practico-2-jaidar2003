package interfaz;

import modelo.EstadoRecurso;

/**
 * Contrato principal para todos los recursos digitales.
 */
public interface IRecursoDigital {
    String getIdentificador();
    EstadoRecurso getEstado();
    void actualizarEstado(EstadoRecurso estado);
}
