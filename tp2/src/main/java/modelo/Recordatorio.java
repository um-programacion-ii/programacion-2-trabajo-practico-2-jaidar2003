package modelo;

import java.time.LocalDateTime;

/**
 * Clase que representa un recordatorio en el sistema.
 * Los recordatorios tienen diferentes niveles de urgencia y están asociados a un usuario.
 */
public class Recordatorio {
    private final Usuario usuario;
    private final String mensaje;
    private final NivelUrgencia nivel;
    private final LocalDateTime fechaCreacion;
    private final String asunto;
    private boolean leido;

    /**
     * Niveles de urgencia para los recordatorios.
     */
    public enum NivelUrgencia {
        INFO, WARNING, ERROR
    }

    /**
     * Constructor para crear un recordatorio.
     * @param usuario Usuario al que va dirigido el recordatorio
     * @param asunto Asunto del recordatorio
     * @param mensaje Mensaje del recordatorio
     * @param nivel Nivel de urgencia del recordatorio
     */
    public Recordatorio(Usuario usuario, String asunto, String mensaje, NivelUrgencia nivel) {
        this.usuario = usuario;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.nivel = nivel;
        this.fechaCreacion = LocalDateTime.now();
        this.leido = false;
    }

    /**
     * Genera un mensaje formateado según el nivel de urgencia.
     * @return Mensaje formateado
     */
    public String getMensajeFormateado() {
        String prefijo = switch (nivel) {
            case INFO -> "ℹ️ [INFO] ";
            case WARNING -> "⚠️ [ADVERTENCIA] ";
            case ERROR -> "🚨 [ERROR] ";
        };
        
        return prefijo + asunto + ": " + mensaje;
    }

    /**
     * Marca el recordatorio como leído.
     */
    public void marcarComoLeido() {
        this.leido = true;
    }

    // Getters
    
    public Usuario getUsuario() {
        return usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public NivelUrgencia getNivel() {
        return nivel;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getAsunto() {
        return asunto;
    }

    public boolean isLeido() {
        return leido;
    }

    @Override
    public String toString() {
        return getMensajeFormateado() + (leido ? " [Leído]" : " [No leído]");
    }
}