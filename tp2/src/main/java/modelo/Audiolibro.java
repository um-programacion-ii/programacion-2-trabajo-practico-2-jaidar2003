package modelo;

public class Audiolibro extends RecursoBase {
    private int duracion; // en minutos
    private String narrador;

    public Audiolibro(String id, String titulo, int duracion, String narrador) {
        super(id, titulo);
        this.duracion = duracion;
        this.narrador = narrador;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getNarrador() {
        return narrador;
    }

    @Override
    public String toString() {
        return super.toString() + " | Audiolibro - Narrador: " + narrador + ", Duración: " + duracion + " min";
    }
}
