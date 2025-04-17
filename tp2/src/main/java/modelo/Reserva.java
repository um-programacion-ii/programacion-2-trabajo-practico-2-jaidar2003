package modelo;

import interfaz.interfazRecursoDigital;

public class Reserva {
    private final Usuario usuario;
    private final interfazRecursoDigital recurso;

    public Reserva(Usuario usuario, interfazRecursoDigital recurso) {
        this.usuario = usuario;
        this.recurso = recurso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public interfazRecursoDigital getRecurso() {
        return recurso;
    }

    @Override
    public String toString() {
        return "📌 Reserva: " + recurso.getTitulo() + " por " + usuario.getNombre();
    }
}
