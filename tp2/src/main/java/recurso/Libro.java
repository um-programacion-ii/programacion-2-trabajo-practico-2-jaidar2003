package recurso;

import interfaz.Prestable;
import interfaz.Renovable;
import modelo.EstadoRecurso;

public class Libro extends RecursoBase implements Prestable, Renovable {
    private String autor;
    private String isbn;
    private boolean prestado;

    public Libro(String id, String titulo, String autor, String isbn) {
        super(id, titulo);
        this.autor = autor;
        this.isbn = isbn;
        this.prestado = false;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
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
    public boolean renovar() {
        System.out.println("📚 Renovación exitosa del libro: " + getTitulo());
        return true;
    }


    @Override
    public String toString() {
        return super.toString() + " | Libro - Autor: " + autor + ", ISBN: " + isbn;
    }
}
