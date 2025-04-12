package recurso;

import interfaz.Prestable;
import modelo.EstadoRecurso;

public class Revista extends RecursoBase implements Prestable {
    private int numeroEdicion;
    private boolean prestado;

    public Revista(String id, String titulo, int numeroEdicion) {
        super(id, titulo);
        this.numeroEdicion = numeroEdicion;
        this.prestado = false;
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    @Override
    public void prestar() {
        if (!prestado) {
            prestado = true;
            actualizarEstado(EstadoRecurso.PRESTADO);
        }
    }

    @Override
    public void devolver() {
        if (prestado) {
            prestado = false;
            actualizarEstado(EstadoRecurso.DISPONIBLE);
        }
    }

    @Override
    public boolean estaPrestado() {
        return prestado;
    }

    @Override
    public String toString() {
        return super.toString() + " | Revista - Edición Nº: " + numeroEdicion;
    }
}
