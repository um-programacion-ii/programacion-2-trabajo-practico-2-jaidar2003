package modelo;

/**
 * Clase que representa las preferencias de notificación de un usuario.
 * Permite configurar cómo y cuándo recibir recordatorios.
 */
public class PreferenciasNotificacion {
    private boolean recibirPorEmail;
    private boolean recibirPorConsola;
    private boolean recibirInfoLevel;
    private boolean recibirWarningLevel;
    private boolean recibirErrorLevel;

    /**
     * Constructor por defecto que establece todas las preferencias a true.
     */
    public PreferenciasNotificacion() {
        // Por defecto, recibir todas las notificaciones
        this.recibirPorEmail = true;
        this.recibirPorConsola = true;
        this.recibirInfoLevel = true;
        this.recibirWarningLevel = true;
        this.recibirErrorLevel = true;
    }

    /**
     * Constructor que permite configurar todas las preferencias.
     * 
     * @param recibirPorEmail Si se deben recibir notificaciones por email
     * @param recibirPorConsola Si se deben recibir notificaciones por consola
     * @param recibirInfoLevel Si se deben recibir notificaciones de nivel INFO
     * @param recibirWarningLevel Si se deben recibir notificaciones de nivel WARNING
     * @param recibirErrorLevel Si se deben recibir notificaciones de nivel ERROR
     */
    public PreferenciasNotificacion(boolean recibirPorEmail, boolean recibirPorConsola,
                                   boolean recibirInfoLevel, boolean recibirWarningLevel,
                                   boolean recibirErrorLevel) {
        this.recibirPorEmail = recibirPorEmail;
        this.recibirPorConsola = recibirPorConsola;
        this.recibirInfoLevel = recibirInfoLevel;
        this.recibirWarningLevel = recibirWarningLevel;
        this.recibirErrorLevel = recibirErrorLevel;
    }

    /**
     * Verifica si el usuario debe recibir un recordatorio según su nivel de urgencia.
     * 
     * @param nivel Nivel de urgencia del recordatorio
     * @return true si el usuario debe recibir el recordatorio, false en caso contrario
     */
    public boolean debeRecibirPorNivel(Recordatorio.NivelUrgencia nivel) {
        return switch (nivel) {
            case INFO -> recibirInfoLevel;
            case WARNING -> recibirWarningLevel;
            case ERROR -> recibirErrorLevel;
        };
    }

    // Getters y setters

    public boolean isRecibirPorEmail() {
        return recibirPorEmail;
    }

    public void setRecibirPorEmail(boolean recibirPorEmail) {
        this.recibirPorEmail = recibirPorEmail;
    }

    public boolean isRecibirPorConsola() {
        return recibirPorConsola;
    }

    public void setRecibirPorConsola(boolean recibirPorConsola) {
        this.recibirPorConsola = recibirPorConsola;
    }

    public boolean isRecibirInfoLevel() {
        return recibirInfoLevel;
    }

    public void setRecibirInfoLevel(boolean recibirInfoLevel) {
        this.recibirInfoLevel = recibirInfoLevel;
    }

    public boolean isRecibirWarningLevel() {
        return recibirWarningLevel;
    }

    public void setRecibirWarningLevel(boolean recibirWarningLevel) {
        this.recibirWarningLevel = recibirWarningLevel;
    }

    public boolean isRecibirErrorLevel() {
        return recibirErrorLevel;
    }

    public void setRecibirErrorLevel(boolean recibirErrorLevel) {
        this.recibirErrorLevel = recibirErrorLevel;
    }

    @Override
    public String toString() {
        return "PreferenciasNotificacion{" +
                "email=" + (recibirPorEmail ? "✓" : "✗") +
                ", consola=" + (recibirPorConsola ? "✓" : "✗") +
                ", INFO=" + (recibirInfoLevel ? "✓" : "✗") +
                ", WARNING=" + (recibirWarningLevel ? "✓" : "✗") +
                ", ERROR=" + (recibirErrorLevel ? "✓" : "✗") +
                '}';
    }
}