package modelo;

import java.util.HashMap;
import java.util.Map;

public class Usuario {
    private final String id;
    private String nombre;
    private String email;
    private Map<String, Boolean> notificationPreferences;

    /**
     * Constructor del usuario.
     * @param id ID único del usuario
     * @param nombre Nombre completo
     * @param email Correo electrónico
     */
    public Usuario(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.notificationPreferences = new HashMap<>();
        this.notificationPreferences.put("email", true);
        this.notificationPreferences.put("sms", false);
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Boolean> getNotificationPreferences() {
        return notificationPreferences;
    }

    public void setNotificationPreferences(String type, boolean enabled) {
        this.notificationPreferences.put(type, enabled);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", notificationPreferences=" + notificationPreferences +
                '}';
    }
}
