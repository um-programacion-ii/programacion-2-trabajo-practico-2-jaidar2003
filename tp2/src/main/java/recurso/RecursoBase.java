package recurso;

import interfaz.RecursoDigital;
import modelo.EstadoRecurso;
import modelo.CategoriaRecurso;

public abstract class RecursoBase implements RecursoDigital {
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

    @Override
    public String getTitulo() {
        return titulo;
    }

    @Override
    public CategoriaRecurso getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaRecurso categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Recurso: " + titulo + " [" + estado + "]" + " | Categoría: " + categoria;
    }
}