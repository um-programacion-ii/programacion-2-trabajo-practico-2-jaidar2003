package recurso;

import interfaz.Prestable;
import interfaz.Renovable;
import interfaz.interfazRecursoDigital;
import interfaz.Accesible;
import modelo.EstadoRecurso;

public class Audiolibro extends RecursoBase implements Prestable, Renovable, interfazRecursoDigital, Accesible {
    private String narrador;

    public Audiolibro(String id, String titulo, String narrador) {
        super(id, titulo);
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
    public void renovar() {
        System.out.println("♻️ Renovación exitosa del audiolibro: " + getTitulo());
    }

    @Override
    public void prestar() {

    }

    @Override
    public void devolver() {

    }

    @Override
    public boolean estaPrestado() {
        return getEstado() == EstadoRecurso.PRESTADO;
    }

    @Override
    public String toString() {
        return super.toString() + " | Audiolibro - Narrador: " + narrador;
    }
}
