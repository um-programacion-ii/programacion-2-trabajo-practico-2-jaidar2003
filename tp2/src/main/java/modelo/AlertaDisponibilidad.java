package modelo;

import interfaz.RecursoDigital;

/**
 * Clase que representa una alerta de disponibilidad de un recurso reservado.
 * Se utiliza para notificar a los usuarios cuando un recurso que habían reservado
 * está disponible para su préstamo.
 */
public class AlertaDisponibilidad {
    private final Usuario usuario;
    private final RecursoDigital recurso;
    private final String mensaje;

    /**
     * Constructor que crea una alerta de disponibilidad a partir de una reserva.
     * @param reserva La reserva para la cual se crea la alerta
     */
    public AlertaDisponibilidad(Reserva reserva) {
        this.usuario = reserva.getUsuario();
        this.recurso = reserva.getRecurso();
        this.mensaje = generarMensaje();
    }

    /**
     * Constructor que crea una alerta de disponibilidad directamente con usuario y recurso.
     * @param usuario El usuario que realizó la reserva
     * @param recurso El recurso que está disponible
     */
    public AlertaDisponibilidad(Usuario usuario, RecursoDigital recurso) {
        this.usuario = usuario;
        this.recurso = recurso;
        this.mensaje = generarMensaje();
    }

    /**
     * Genera el mensaje de notificación para la alerta.
     * @return El mensaje formateado
     */
    private String generarMensaje() {
        return "🎉 ¡Buenas noticias! El recurso \"" + recurso.getTitulo() + 
               "\" que reservaste ya está disponible para préstamo. " +
               "Puedes pasar a retirarlo o usar la opción 'P' para préstamo inmediato.";
    }

    /**
     * Obtiene el usuario asociado a la alerta.
     * @return El usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Obtiene el recurso asociado a la alerta.
     * @return El recurso
     */
    public RecursoDigital getRecurso() {
        return recurso;
    }

    /**
     * Obtiene el mensaje de la alerta.
     * @return El mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    @Override
    public String toString() {
        return mensaje;
    }
}