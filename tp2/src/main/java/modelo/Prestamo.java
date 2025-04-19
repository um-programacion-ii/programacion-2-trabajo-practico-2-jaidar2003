package modelo;

import interfaz.RecursoDigital;

import java.time.LocalDate;

public class Prestamo {
    private Usuario usuario;
    private RecursoDigital recurso;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Prestamo(Usuario usuario, RecursoDigital recurso, LocalDate fechaInicio, LocalDate fechaFin) {
        this.usuario = usuario;
        this.recurso = recurso;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public RecursoDigital getRecurso() {
        return recurso;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaFin);
    }

    @Override
    public String toString() {
        return String.format("📚 Préstamo - Recurso: %s | Usuario: %s | Desde: %s | Hasta: %s%s",
                recurso.getTitulo(),
                usuario.getNombre(),
                fechaInicio,
                fechaFin,
                estaVencido() ? " ⚠️ (VENCIDO)" : "");
    }
}
