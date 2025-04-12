package recurso;

import interfaz.Prestable;
import interfaz.Renovable;

/**
 * Recurso concreto: Libro. Implementa interfaces Prestable y Renovable.
 */
public class Libro extends RecursoBase implements Prestable, Renovable {
    private String autor;
    private String isbn;

    public Libro(String id, String titulo, String autor, String isbn) {
        super(id, titulo);
        this.autor = autor;
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public boolean estaPrestado() {
        return getEstado().equals(modelo.EstadoRecurso.PRESTADO);
    }

    @Override
    public void prestar() {
        actualizarEstado(modelo.EstadoRecurso.PRESTADO);
    }

    @Override
    public void devolver() {
        actualizarEstado(modelo.EstadoRecurso.DISPONIBLE);
    }

    @Override
    public void renovar() {
        System.out.println("📚 Renovación exitosa del libro: " + getTitulo());
    }

    @Override
    public String toString() {
        return super.toString() + " | Libro - Autor: " + autor + ", ISBN: " + isbn;
    }
}
