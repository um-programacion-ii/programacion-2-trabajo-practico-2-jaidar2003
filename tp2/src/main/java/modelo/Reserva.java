package modelo;

import interfaz.RecursoDigital;

public class Reserva {
    private final Usuario usuario;
    private final RecursoDigital recurso;

    public Reserva(Usuario usuario, RecursoDigital recurso) {
        this.usuario = usuario;
        this.recurso = recurso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public RecursoDigital getRecurso() {
        return recurso;
    }

    @Override
    public String toString() {
        return "📌 Reserva: " + recurso.getTitulo() + " por " + usuario.getNombre();
    }
}
