package modelo;

public class Libro extends RecursoBase {
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
    public String toString() {
        return super.toString() + " | Libro - Autor: " + autor + ", ISBN: " + isbn;
    }
}
