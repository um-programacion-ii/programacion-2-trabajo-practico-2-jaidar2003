package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AlertaVencimiento {
    private final Prestamo prestamo;
    private final NivelUrgencia urgencia;
    private final String mensaje;

    public enum NivelUrgencia {
        INFO, WARNING, CRITICAL
    }

    public AlertaVencimiento(Prestamo prestamo) {
        this.prestamo = prestamo;
        this.urgencia = calcularNivelUrgencia();
        this.mensaje = generarMensaje();
    }

    private NivelUrgencia calcularNivelUrgencia() {
        long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), prestamo.getFechaFin());

        if (diasRestantes == 1) return NivelUrgencia.WARNING;
        else if (diasRestantes == 0) return NivelUrgencia.CRITICAL;
        else return NivelUrgencia.INFO;
    }

    private String generarMensaje() {
        String base = "📅 El recurso \"" + prestamo.getRecurso().getTitulo() + "\" prestado por "
                + prestamo.getUsuario().getNombre();
        String renovacionInfo = " - Para renovar, use la opción 'R'";

        return switch (urgencia) {
            case CRITICAL -> "⚠️ [CRÍTICO] " + base + " vence HOY (" + prestamo.getFechaFin() + ")" + renovacionInfo;
            case WARNING -> "🔔 [AVISO] " + base + " vence mañana (" + prestamo.getFechaFin() + ")" + renovacionInfo;
            case INFO -> "✅ " + base + " no está por vencer.";
        };
    }

    public NivelUrgencia getUrgencia() {
        return urgencia;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }
}
