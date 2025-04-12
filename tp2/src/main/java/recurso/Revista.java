package recurso;

public class Revista extends RecursoBase {
    private int numeroEdicion;

    public Revista(String id, String titulo, int numeroEdicion) {
        super(id, titulo);
        this.numeroEdicion = numeroEdicion;
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    @Override
    public String toString() {
        return super.toString() + " | Revista - Edición Nº: " + numeroEdicion;
    }
}
