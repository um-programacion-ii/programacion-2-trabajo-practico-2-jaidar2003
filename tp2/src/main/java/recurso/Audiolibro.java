package recurso;

import interfaz.Accesible;
import interfaz.Prestable;
import interfaz.Renovable;
import modelo.CategoriaRecurso;
import modelo.EstadoRecurso;

public class Audiolibro extends RecursoBase implements Accesible, Prestable, Renovable {
    private String narrador;

    public Audiolibro(String id, String titulo, String narrador, CategoriaRecurso categoria) {
        super(id, titulo, categoria);
        this.narrador = narrador;
    }

    public String getNarrador() {
        return narrador;
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
        System.out.println("📚 Renovación exitosa del audiolibro: " + getTitulo());
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