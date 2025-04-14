package recurso;

import interfaz.Accesible;
import modelo.CategoriaRecurso;
import modelo.EstadoRecurso;

public class Audiolibro extends RecursoBase implements Accesible {
    private String narrador;

    public Audiolibro(String id, String titulo, String narrador, CategoriaRecurso categoria) {
        super(id, titulo, categoria); // pasamos la categoría al padre
        this.narrador = narrador;
    }

    public String getNarrador() {
        return narrador;
    }

    @Override
    public void accederEnLinea() {
        System.out.println("🔊 Accediendo en línea al audiolibro: " + getTitulo());
    }

    @Override
    public void descargar() {
        System.out.println("⬇️ Descargando el audiolibro: " + getTitulo());
    }

    @Override
    public String toString() {
        return super.toString() + " | Audiolibro - Narrador: " + narrador;
    }
}
