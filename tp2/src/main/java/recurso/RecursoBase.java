package recurso;

import modelo.EstadoRecurso;
import modelo.CategoriaRecurso;
import interfaz.interfazRecursoDigital;

public abstract class RecursoBase implements interfazRecursoDigital {
    private String id;
    private String titulo;
    private EstadoRecurso estado;
    private CategoriaRecurso categoria;

    public RecursoBase(String id, String titulo, CategoriaRecurso categoria) {
        this.id = id;
        this.titulo = titulo;
        this.estado = EstadoRecurso.DISPONIBLE;
        this.categoria = categoria;
    }

    public String getIdentificador() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public EstadoRecurso getEstado() {
        return estado;
    }

    public void actualizarEstado(EstadoRecurso estado) {
        this.estado = estado;
    }

    public CategoriaRecurso getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaRecurso categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Recurso: " + titulo + " [" + estado + "] - Categoría: " + categoria;
    }
}
