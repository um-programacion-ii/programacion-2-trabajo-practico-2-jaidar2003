package modelo;

/**
 * Clase abstracta base para todos los recursos digitales.
 */
public abstract class RecursoBase implements RecursoDigital {
    private final String id;
    private String titulo;
    private EstadoRecurso estado;

    public RecursoBase(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.estado = EstadoRecurso.DISPONIBLE;
    }

    @Override
    public String getIdentificador() {
        return id;
    }

    @Override
    public EstadoRecurso getEstado() {
        return estado;
    }

    @Override
    public void actualizarEstado(EstadoRecurso nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Recurso: " + titulo + " [" + estado + "]";
    }
}
