package recurso;

import interfaz.interfazRecursoDigital;
import modelo.EstadoRecurso;

/**
 * Clase abstracta que implementa IRecursoDigital.
 * Sirve como base para recursos concretos como Libro, Revista, etc.
 */
public abstract class RecursoBase implements interfazRecursoDigital {
    private String id;
    private String titulo;
    private EstadoRecurso estado;

    public RecursoBase(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.estado = EstadoRecurso.DISPONIBLE;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String getIdentificador() {
        return id;
    }

    @Override
    public EstadoRecurso getEstado() {
        return estado;
    }

    @Override
    public void actualizarEstado(EstadoRecurso nuevoEstado) {
        this.estado = nuevoEstado;
    }

    @Override
    public String toString() {
        return "Recurso: " + titulo + " [" + estado + "]";
    }
}
