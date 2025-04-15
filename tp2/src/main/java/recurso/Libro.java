package recurso;

import interfaz.Prestable;
import interfaz.Renovable;
import modelo.CategoriaRecurso;
import modelo.EstadoRecurso;

public class Libro extends RecursoBase implements Prestable, Renovable {
    private String autor;
    private String isbn;

    public Libro(String id, String titulo, String autor, String isbn, CategoriaRecurso categoria) {
        super(id, titulo, categoria);
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
        return getEstado() == EstadoRecurso.PRESTADO;
    }

    @Override
    public void prestar() {
        actualizarEstado(EstadoRecurso.PRESTADO);
    }

    @Override
    public void devolver() {
        actualizarEstado(EstadoRecurso.DISPONIBLE);
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
