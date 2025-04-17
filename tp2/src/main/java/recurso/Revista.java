package recurso;

import interfaz.Prestable;
import modelo.CategoriaRecurso;
import modelo.EstadoRecurso;

public class Revista extends RecursoBase implements Prestable {
    private int numeroEdicion;

    public Revista(String id, String titulo, int numeroEdicion, CategoriaRecurso categoria) {
        super(id, titulo, categoria);
        this.numeroEdicion = numeroEdicion;
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    @Override
    public boolean estaPrestado() {
        return getEstado() == EstadoRecurso.PRESTADO;
    }

    @Override
    public boolean estaDisponible() {return getEstado() == EstadoRecurso.DISPONIBLE;}


    @Override
    public void prestar() {
        actualizarEstado(EstadoRecurso.PRESTADO);
    }

    @Override
    public void devolver() {
        actualizarEstado(EstadoRecurso.DISPONIBLE);
    }

    @Override
    public String toString() {
        return super.toString() + " | Revista - Edición Nº: " + numeroEdicion;
    }
}
